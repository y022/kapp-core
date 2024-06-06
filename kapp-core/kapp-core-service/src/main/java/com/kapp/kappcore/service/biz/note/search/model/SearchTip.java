package com.kapp.kappcore.service.biz.note.search.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchTip {

    private String status;

    private String tip;


    public static SearchTip nullTip() {
        return new SearchTip("901", "检索目标源不能为空");
    }

    public boolean exception() {
        return StringUtils.hasText(getStatus());
    }

}
