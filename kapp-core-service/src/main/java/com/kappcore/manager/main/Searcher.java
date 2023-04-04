package com.kappcore.manager.main;

import com.kappcore.domain.map.SearchResult;
import com.kappcore.manager.context.SearchContext;

public interface Searcher extends SearchControl{
    SearchResult search(SearchContext context);
}
