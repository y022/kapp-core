package com.kapp.kappcore.model.biz.request;

import lombok.Data;

@Data
public class SearchRequestDTO {
    private String title;
    private String body;
    private String tag;
    private boolean highlight;

    private int searchPage;
    private int searchSize;
}
