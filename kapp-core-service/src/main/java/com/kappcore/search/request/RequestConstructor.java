package com.kappcore.search.request;

import com.kappcore.search.context.SearchContext;

public interface RequestConstructor<T> {

    T get(SearchContext searchContext);

}
