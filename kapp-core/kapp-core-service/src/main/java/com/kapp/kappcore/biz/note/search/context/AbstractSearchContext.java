package com.kapp.kappcore.biz.note.search.context;

import com.kapp.kappcore.biz.note.search.context.obj.SearchSource;
import com.kapp.kappcore.biz.note.search.context.obj.SearchTarget;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public abstract class AbstractSearchContext implements SearchContext {
    protected SearchSource source;
    protected SearchTarget target;
    protected int searchPage = 1;
    protected int searchSize = 100;

    @Override
    public String tag() {
        return getSource().getTag();
    }

    @Override
    public SearchTarget target() {
        return getTarget();
    }

    @Override
    public SearchSource source() {
        return getSource();
    }

    @Override
    public boolean emptyValue() {
        return getSource() == null;
    }

    @Override
    public int searchPage() {
        return getSearchPage();
    }

    @Override
    public int searchSize() {
        return getSearchSize();
    }

    @Override
    public void wireSearchVal(SearchSource source, int searchPage, int searchSize, String... highlight) {
        setSource(source);
        if (searchPage != 0) setSearchPage(searchPage);
        if (searchSize != 0) setSearchSize(searchSize);
    }
}
