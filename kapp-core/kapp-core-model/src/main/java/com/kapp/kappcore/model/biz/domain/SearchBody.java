package com.kapp.kappcore.model.biz.domain;

import com.kapp.kappcore.model.biz.Sch;
import lombok.Data;

import java.util.Map;

@Data
public class SearchBody implements Sch {
    private String docId;
    private String body;
    private Map<String, Object> highlightContent;

    @Override
    public String body() {
        return getBody();
    }

    @Override
    public Map<String, Object> highlightContent() {
        return getHighlightContent();
    }
}
