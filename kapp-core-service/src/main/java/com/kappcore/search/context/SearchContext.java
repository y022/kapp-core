package com.kappcore.search.context;

import com.kappcore.search.context.obj.SearchTarget;

public interface SearchContext {

    boolean existSearchValue();

    String tag();

    boolean highlight();

    SearchTarget target();



}
