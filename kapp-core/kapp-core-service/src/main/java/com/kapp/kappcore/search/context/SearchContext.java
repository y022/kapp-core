package com.kapp.kappcore.search.context;

import com.kapp.kappcore.search.context.obj.SearchSource;
import com.kapp.kappcore.search.context.obj.SearchTarget;

public interface SearchContext {

    boolean noSearchValue();

    String tag();

    boolean highlight();

    SearchTarget target();

    SearchSource source();
    int searchPage();

    int searchSize();


}
