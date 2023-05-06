package com.kappcore.search.context.obj;

public class ComplexSearchResult implements SearchResult {

    private String docId;
    private String title;
    private String body;
    private String tag;
    private String saveDate;
    private String owner;
    private long took;
    private long total;
    private long currentPage;

    @Override
    public long took() {
        return 0;
    }


}
