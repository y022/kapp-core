package com.kapp.kappcore.web.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRespVo {
    private long total;
    private long took;
    private List<SearchResultItem> results;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchResultItem {
        private String docId;
        private String title;
        private String[] body;
        private String tag;
        private String saveDate;
        private String owner;

    }
}
