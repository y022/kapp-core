package com.kappcore.search.context;

import com.kappcore.search.context.obj.SearchSource;
import com.kappcore.search.context.obj.SearchTarget;

public abstract class AbsSearchContext implements SearchContext {

    public SearchSource source;

    public SearchTarget target;


    SearchTarget providerSearchTarget() {
        return this.target;
    }

    @Override
    public SearchTarget target() {
        return providerSearchTarget();
    }

    @Override
    public boolean existSearchValue() {
        return source != null;
    }

}
