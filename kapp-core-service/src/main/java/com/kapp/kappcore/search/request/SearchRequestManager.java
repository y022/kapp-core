package com.kapp.kappcore.search.request;

import com.kapp.kappcore.search.context.SearchContext;
import com.kapp.kappcore.search.context.obj.SearchSource;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SearchRequestManager extends AbstractRequestConstructor {


    @Override
    public SearchRequest get(SearchContext searchContext) {

        SearchRequest searchRequest = new SearchRequest();

        if (searchContext.noSearchValue()) {
            searchRequest.source(searchAll());
            return searchRequest;
        }


        SearchSourceBuilder newSourceBuilder = new SearchSourceBuilder();
        SearchSource source = searchContext.source();
        if (StringUtils.hasText(source.getBody())) {
            MatchQueryBuilder body = QueryBuilders.matchQuery("body", source.getBody());
            newSourceBuilder.query(body);
        }

        searchRequest.source(newSourceBuilder);
        return searchRequest;
    }


}
