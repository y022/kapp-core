package com.kapp.kappcore.web.vo.response;

import lombok.Data;

@Data
public class SearchResultVo {
    private String docId;
    private String title;
    private String[] body;
    private String tag;
    private String saveDate;
    private String owner;


}