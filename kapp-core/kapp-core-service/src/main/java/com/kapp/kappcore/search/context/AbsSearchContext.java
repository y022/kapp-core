package com.kapp.kappcore.search.context;

import com.kapp.kappcore.search.context.obj.SearchSource;
import com.kapp.kappcore.search.context.obj.SearchTarget;
import lombok.Data;

@Data
public abstract class AbsSearchContext implements SearchContext {

    public SearchSource source;

    public SearchTarget target;

    public int searchPage;

    public int searchPageSize;


    SearchTarget providerSearchTarget() {
        return this.target;
    }

    @Override
    public SearchTarget target() {
        return providerSearchTarget();
    }

    @Override
    public SearchSource source() {
        return source;
    }

    @Override
    public boolean noSearchValue() {
        return source == null;
    }

    @Override
    public int searchPage() {
        return searchPage;
    }

    @Override
    public int searchSize() {
        return searchPageSize;
    }
}
