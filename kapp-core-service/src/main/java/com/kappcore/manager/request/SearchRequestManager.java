package com.kappcore.manager.request;

import com.kappcore.manager.context.SearchContext;
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
