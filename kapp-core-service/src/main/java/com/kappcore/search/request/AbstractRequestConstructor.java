package com.kappcore.search.request;

import com.kappcore.search.context.SearchContext;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public abstract class AbstractRequestConstructor implements RequestConstructor<SearchRequest> {

    public SearchSourceBuilder searchAll(SearchContext context) {
        SearchSourceBuilder newSourceBuilder = new SearchSourceBuilder();

        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();

        newSourceBuilder.query(matchAllQueryBuilder);

        return newSourceBuilder;
    }
}
