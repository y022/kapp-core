package com.kapp.kappcore.search.support.builder.impl;

import com.kapp.kappcore.search.support.model.SearchLimiter;
import com.kapp.kappcore.search.support.model.condition.GroupCondition;
import com.kapp.kappcore.search.support.model.constant.Val;
import com.kapp.kappcore.search.support.model.param.GroupParamUnit;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.model.param.ViewParam;
import com.kapp.kappcore.search.support.option.DocOption;
import com.kapp.kappcore.search.support.option.GroupOption;
import com.kapp.kappcore.search.support.option.ViewType;
import com.kapp.kappcore.search.support.option.sort.SortRule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValuesSourceAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Author:Heping
 * Date: 2024/6/25 20:09
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchRequestFactory extends AbstractSearchRequestFactory<DocOption, SearchParam, SearchRequest> {
    private static final SearchRequestFactory INSTANCE = new SearchRequestFactory();
    public static SearchRequestFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public SearchRequest create(SearchParam t) {
        return create(t, t.getCondition().option());
    }

    private SearchRequest create(SearchParam param, DocOption option) {
        SearchRequest request = init(param.getSearchIndex());
        SearchSourceBuilder sourceBuilder = null;
        switch (option) {
            case GROUP:
                sourceBuilder = group(param);
                break;
            case SEARCH:
                sourceBuilder = search(param);
                break;
            default:
                //
        }
        request.source(sourceBuilder);
        return request;
    }

    /**
     * 设置必要的分组请求的值
     *
     * @param param p
     */
    protected SearchSourceBuilder group(SearchParam param) {
        SearchSourceBuilder sourceBuilder = searchSourceBuilder();

        //设置不返回详细信息
        sourceBuilder.size(0);
        GroupCondition condition = (GroupCondition) param.getCondition();
        Map<GroupOption, Set<String>> groupKeyMap = condition.getGroupParamUnits().stream().collect(Collectors.groupingBy(GroupParamUnit::getGroupOption, Collectors.mapping(GroupParamUnit::getGroupKey, Collectors.toSet())));
        SearchLimiter searchLimiter = param.getSearchLimiter();
        ViewParam viewParam = searchLimiter.getViewParam();
        groupKeyMap.forEach((keys, value) -> {
            switch (keys) {
                case COUNT:
                    buildAgg(value, (key) -> AggregationBuilders.count(key + GroupOption.COUNT.getDesc() + Val._AGG), sourceBuilder);
                    break;
                case MAX:
                    buildAgg(value, (key) -> AggregationBuilders.max(key + GroupOption.MAX.getDesc() + Val._AGG), sourceBuilder);
                    break;
                case MIN:
                    buildAgg(value, (key) -> AggregationBuilders.min(key + GroupOption.MIN.getDesc() + Val._AGG), sourceBuilder);
                    break;
                case SUM:
                    buildAgg(value, (key) -> AggregationBuilders.sum(key + GroupOption.SUM.getDesc() + Val._AGG), sourceBuilder);
                    break;
                case AVG:
                    buildAgg(value, (key) -> AggregationBuilders.avg(key + GroupOption.AVG.getDesc() + Val._AGG), sourceBuilder);
                    break;
                case STATS:
                    buildAgg(value, (key) -> AggregationBuilders.stats(key + GroupOption.STATS.getDesc() + Val._AGG), sourceBuilder);
                    break;
                case BUCKET:
                    buildAgg(value, (aggKey) -> {
                        TermsAggregationBuilder tab = aggByBucket(aggKey);
                        /**
                         * 只有在 {{@link ViewType.TIP}}模式下，才会设置桶内详细信息，并且只返回id和title
                         */
                        if (ViewType.TIP.equals(viewParam.getViewType())) {
                            tab.subAggregation(AggregationBuilders.topHits("top_hits").fetchSource(new String[]{Val.ID, Val.TITLE}, null).size(searchLimiter.getPageSize()));
                        }
                        return tab;
                    }, sourceBuilder);
            }
        });
        sourceBuilder.size(0);
        return sourceBuilder;
    }

    @SuppressWarnings("all")
    private void buildAgg(Set<String> keys, Function<String, ValuesSourceAggregationBuilder> function, SearchSourceBuilder sourceBuilder) {
        for (String key : keys) {
            sourceBuilder.aggregation(function.apply(key).field(key));
        }
    }

    protected SearchSourceBuilder search(SearchParam param) {
        SearchSourceBuilder sourceBuilder = Query.searchSourceBuilder();

        //page and sort
        SearchLimiter searchLimiter = param.getSearchLimiter();
        sourceBuilder
                .from((searchLimiter.getPageNum() - 1) * searchLimiter.getPageSize())
                .size(searchLimiter.getPageSize());

        if (searchLimiter.getSortRule().isAssign(SortRule.FIELD) && CollectionUtils.isNotEmpty(searchLimiter.getSortParams())) {
            searchLimiter.getSortParams().forEach((sp) -> sourceBuilder.sort(Sort.sortBuilder(sp.getSortKey(), sp.getSortType().getCode())));
        }

        QueryBuilder queryBuilder = MultiQuerySentenceFactory.getInstance().create(param);
        sourceBuilder.query(queryBuilder);
        ViewParam viewParam = searchLimiter.getViewParam();
        String[] viewFields = null;
        switch (viewParam.getViewType()) {
            case SIMPLE:
                viewFields = new String[]{Val.ID};
                break;
            case TIP:
                viewFields = new String[]{Val.ID, Val.TITLE};
                break;
            case SET:
                viewFields = viewParam.getViewFields().toArray(new String[0]);
                break;
        }
        if (viewFields != null) {
            sourceBuilder.fetchSource(viewFields, null);
        }
        return sourceBuilder;

    }
}


