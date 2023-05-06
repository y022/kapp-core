package com.kappcore.search.request;

import com.kappcore.search.context.SearchContext;
import org.elasticsearch.action.search.SearchRequest;
import org.springframework.stereotype.Component;

@Component
public class SearchRequestManager extends AbstractRequestConstructor {


    @Override
    public SearchRequest get(SearchContext searchContext) {

        SearchRequest searchRequest = new SearchRequest();

        if (!searchContext.existSearchValue()) {
            searchRequest.source(searchAll(searchContext));
            return searchRequest;
        }


        return searchRequest;
    }
}
