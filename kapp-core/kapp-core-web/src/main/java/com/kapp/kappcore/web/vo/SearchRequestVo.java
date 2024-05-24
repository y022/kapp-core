package com.kapp.kappcore.web.vo;

import lombok.Data;

@Data
public class SearchRequestVo {
    private String title;
    private String body;
    private String tag;
    private boolean highlight;
    private int searchSize;
    private int searchPage;
}
