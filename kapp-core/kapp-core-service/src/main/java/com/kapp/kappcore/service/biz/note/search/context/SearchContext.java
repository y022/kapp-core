package com.kapp.kappcore.service.biz.note.search.context;


import com.kapp.kappcore.service.biz.note.search.context.obj.SearchSource;
import com.kapp.kappcore.service.biz.note.search.context.obj.SearchTarget;

import java.util.Set;

public interface SearchContext {
    String tag();

    boolean emptyValue();

    SearchTarget target();

    SearchSource source();

    int searchPage();

    int searchSize();

    boolean requireHighlight();

    Set<String> highlightFields();

    void wireSearchVal(SearchSource source, int searchPage, int searchSize, String... highlight);
}
