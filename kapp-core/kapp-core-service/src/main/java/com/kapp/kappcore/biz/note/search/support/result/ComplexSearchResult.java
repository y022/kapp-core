package com.kapp.kappcore.biz.note.search.support.result;

import lombok.Data;

import java.util.List;

@Data
public class ComplexSearchResult implements SearchResult {

    private List<ISearchBody> body;


    private long took;
    private long total;
    private long searchPage;
    private long searchPageSize;

    @Override
    public long took() {
        return this.took;
    }

    @Override
    public List<ISearchBody>  body() {
        return this.body;
    }


}
