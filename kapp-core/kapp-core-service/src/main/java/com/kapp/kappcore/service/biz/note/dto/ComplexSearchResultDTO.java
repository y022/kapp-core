package com.kapp.kappcore.service.biz.note.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplexSearchResultDTO {
    private long total;
    private long took;
    private List<ComplexSearchDTO> results;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ComplexSearchDTO {
        private String title;
        private String[] body;
        private String tag;
        private String saveDate;
        private String owner;
        private String docId;
    }
}
