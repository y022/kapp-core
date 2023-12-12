package com.kapp.kappcore.biz.note.dto;


import lombok.Data;

@Data
public class ComplexSearchResultDTO {
    private String title;
    private String[] body;
    private String tag;
    private String saveDate;
    private String owner;
    private String docId;
}
