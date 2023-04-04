package com.kappcore.manager.request;

import com.kappcore.manager.context.SearchContext;

public interface RequestConstructor<T> {

    T get(SearchContext searchContext);

}
