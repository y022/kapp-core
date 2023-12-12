package com.kapp.kappcore.biz.note.search.context;

import com.kapp.kappcore.biz.note.search.context.obj.SearchSource;
import com.kapp.kappcore.biz.note.search.context.obj.SearchTarget;

public interface SearchContext {

    boolean noSearchValue();

    String tag();

    boolean highlight();

    SearchTarget target();

    SearchSource source();
    int searchPage();

    int searchSize();


}
