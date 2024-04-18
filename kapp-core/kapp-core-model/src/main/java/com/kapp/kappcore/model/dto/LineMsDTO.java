package com.kapp.kappcore.model.dto;

import lombok.Data;

@Data
public class LineMsDTO {
    private String title;
    private String[] body;
    private String tag;
    private String date;
    private String owner;
    private String docId;
    private String version;
    private int bodyLength;
}
