package com.kapp.kappcore.biz.note.search.request;

import com.kapp.kappcore.biz.note.search.context.SearchContext;

public interface RequestConstructor<T> {

    T get(SearchContext searchContext);

}
