package com.kapp.kappcore.model.page;

import lombok.Data;

@Data
public class PageAndSort {
    private int pageNum = 1;
    private int pageSize = 10;
    private String sort;
    private boolean asc;
    private long total;

    public PageAndSort wireTotal(long total) {
        setTotal(total);
        return this;
    }

    public static PageAndSort defaultOrder() {
        return new PageAndSort();
    }
}
