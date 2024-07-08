package com.kapp.kappcore.search.support.builder.impl;

import com.kapp.kappcore.search.support.model.condition.SearchCondition;
import com.kapp.kappcore.search.support.model.param.HitParam;
import com.kapp.kappcore.search.support.model.param.RangeParam;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.model.param.SearchParamUnit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;

/**
 * Author:Heping
 * Date: 2024/6/28 17:28
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MultiQuerySentenceFactory extends AbstractSearchRequestFactory<Boolean, SearchParam, QueryBuilder> {
    private static final MultiQuerySentenceFactory INSTANCE = new MultiQuerySentenceFactory();

    public static MultiQuerySentenceFactory getInstance() {
        return INSTANCE;
    }


    @Override
    public QueryBuilder create(SearchParam searchParam) {
        SearchCondition condition = (SearchCondition) searchParam.getCondition();
        if (condition.isSearchAll()) {
            return Query.boolQuery();
        }
        BoolQueryBuilder queryBuilder = Query.boolQuery();
        List<SearchParamUnit> paramUnits = condition.hasMultiCondition() ? condition.getSearchParamUnits() : condition.toParamUnit();
        for (SearchParamUnit paramUnit : paramUnits) {
            QueryBuilder subQueryBuilder = create(paramUnit);
            switch (paramUnit.getMultiQueryRule()) {
                case MUST:
                    queryBuilder.must(subQueryBuilder);
                    break;
                case MUST_NOT:
                    queryBuilder.mustNot(subQueryBuilder);
                    break;
                case SHOULD:
                    queryBuilder.should(subQueryBuilder);
                    break;
                case FILTER:
                    queryBuilder.filter(queryBuilder);
                    break;
            }
        }
        return queryBuilder;
    }


    protected QueryBuilder create(SearchParamUnit paramUnit) {
        QueryBuilder queryBuilder = null;
        HitParam hitParam = paramUnit.getHitParam();
        String key = paramUnit.getKey();
        Object value = paramUnit.getValue();
        switch (hitParam.getContHitStrategy()) {
            case ACCURATE:
                queryBuilder = Query.term(key, value);
                break;
            case PARTICIPLE:
                queryBuilder = Query.match(key, value);
                break;
            case FUZZY:
                queryBuilder = Query.fuzzy(key, value);
                break;
            case RANGE:
                RangeParam rangeParam = paramUnit.getRangeParam();
                queryBuilder = Query.range(key, rangeParam.getLte(), rangeParam.getGte(), rangeParam.isIncludeLow(), rangeParam.isIncludeHigh());
                break;
            default:
                // nothing to do
        }
        return queryBuilder;
    }
}
