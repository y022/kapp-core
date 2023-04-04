package com.kappcore.manager.context;

import com.kappcore.manager.context.obj.SearchSource;
import com.kappcore.manager.context.obj.SearchTarget;

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
