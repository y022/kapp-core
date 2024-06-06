package com.kapp.kappcore.service.biz.note.search.context.obj;

import lombok.Data;

@Data
public class SearchSource {
    private String title;
    private String body;
    private String tag;
    private String saveDate;
    private String owner;
    private boolean highlight;
}
