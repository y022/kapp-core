package com.kapp.kappcore.model.biz.domain.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchGroup implements Group {
    private long count;
    private String key;
    private List<GroupBody> content;


    public SearchGroup(List<GroupBody> content) {
        this.content = content;
        this.count = content != null ? content.size() : 0;
    }

    @Override
    public long count() {
        return getCount();
    }

    @Override
    public String body() {
        return "";
    }

    @Override
    public Map<String, Object> highlightContent() {
        return Map.of();
    }


}
