package com.kapp.kappcore.service.biz.note.search.request;

import com.kapp.kappcore.model.biz.domain.group.GroupType;
import com.kapp.kappcore.model.constant.SearchVal;
import com.kapp.kappcore.service.biz.note.search.context.GroupSearchContext;
import com.kapp.kappcore.service.biz.note.search.context.SearchContext;
import com.kapp.kappcore.service.biz.note.search.context.obj.SearchSource;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;

public class RequestConstructor extends AbstractRequestConstructor {

    public static final RequestConstructor CONSTRUCTOR = new RequestConstructor();

    public SearchRequest normalSearch(SearchContext searchContext) {
        SearchRequest searchRequest = init(searchContext.tag());
        if (searchContext.emptyValue()) {
            searchRequest.source(searchAll());
            return searchRequest;
        }
        SearchSource source = searchContext.source();
        SearchSourceBuilder searchSourceBuilder = searchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = boolQueryBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        MultiQuery(source.getBody(), SearchVal.SEARCH_BODY, SearchVal.MUST, boolQueryBuilder);
        MultiQuery(source.getTitle(), SearchVal.SEARCH_TITLE, SearchVal.MUST, boolQueryBuilder);
        MultiQuery(source.getTag(), SearchVal.SEARCH_TAG, SearchVal.MUST, boolQueryBuilder);
        if (searchContext.requireHighlight())
            highlight(SearchVal.HIGHLIGHT_PRE_TAG_1, SearchVal.HIGHLIGHT_POST_TAG_1, searchSourceBuilder, searchContext.highlightFields().toArray(new String[0]));

        searchSourceBuilder.from(searchContext.searchPage());
        searchSourceBuilder.size(searchContext.searchSize());
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }


    public SearchRequest group(GroupSearchContext groupContext) {
        SearchRequest searchRequest = init(groupContext.tag());
        SearchSourceBuilder searchSourceBuilder = searchSourceBuilder();
        List<TermsAggregationBuilder> allTb = aggByBuckets(groupContext.getGroupFields(), groupContext.getSubGroupFields());
        if (!isEmpty(allTb)) {
            for (TermsAggregationBuilder tb : allTb) {
                searchSourceBuilder.aggregation(tb);
            }
        }
        //The first 100 buckets are selected by default
        searchSourceBuilder.size(GroupType.COUNT.equals(groupContext.getGroupType()) ? 0 : 100);
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }
}
