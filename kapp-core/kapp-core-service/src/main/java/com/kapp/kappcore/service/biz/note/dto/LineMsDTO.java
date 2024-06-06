package com.kapp.kappcore.service.biz.note.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineMsDTO {
    private String id;
    private String no;
    private String content;
    private String version;
    private String date;
    private String tag;
}
