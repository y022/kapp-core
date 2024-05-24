package com.kapp.kappcore.model.biz.domain.group;

import com.kapp.kappcore.model.biz.Sch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupBody implements Sch {
    private long docCount;
    private String groupField;



    @Override
    public String body() {
        return "";
    }

    @Override
    public Map<String, Object> highlightContent() {
        return null;
    }
}