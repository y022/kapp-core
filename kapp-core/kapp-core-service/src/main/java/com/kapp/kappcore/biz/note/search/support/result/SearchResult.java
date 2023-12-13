package com.kapp.kappcore.biz.note.search.support.result;

import java.util.List;

public interface SearchResult {
    long took();
    List<ISearchBody> body();



}
