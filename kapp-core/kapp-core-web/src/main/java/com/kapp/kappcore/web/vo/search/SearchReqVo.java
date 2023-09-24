package com.kapp.kappcore.web.vo.search;

import lombok.Data;

@Data
public class SearchReqVo {
    private String title;
    private String body;
    private String tag;
    private String saveDate;
    private String owner;
}
