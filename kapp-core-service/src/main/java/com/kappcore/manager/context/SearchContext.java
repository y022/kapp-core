package com.kappcore.manager.context;

import com.kappcore.manager.context.obj.SearchTarget;

public interface SearchContext {

    boolean existSearchValue();

    String tag();

    boolean highlight();

    SearchTarget target();



}
