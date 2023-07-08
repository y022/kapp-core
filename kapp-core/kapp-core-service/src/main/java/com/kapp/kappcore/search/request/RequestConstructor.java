package com.kapp.kappcore.search.request;

import com.kapp.kappcore.search.context.SearchContext;

public interface RequestConstructor<T> {

    T get(SearchContext searchContext);

}
