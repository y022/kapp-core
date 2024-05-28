package com.kapp.kappcore.model.page;

import lombok.Data;

@Data
public class PageAndSort {
    private int pageNum;
    private int pageSize;
    private String sort;
    private boolean asc;
    private long total;
}
