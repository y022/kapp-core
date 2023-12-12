package com.kapp.kappcore.biz.note.search.request;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public abstract class AbstractRequestConstructor implements RequestConstructor<SearchRequest> {


    public SearchSourceBuilder searchAll() {
        SearchSourceBuilder newSourceBuilder = new SearchSourceBuilder();

        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();

        newSourceBuilder.query(matchAllQueryBuilder);

        return newSourceBuilder;
    }
}
