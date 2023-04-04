package com.kapp.kappcore.web.vo.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.UUID;

@Data
public class SearchResultVo {
    private String docId;
    private String title;
    private String body;
    private String tag;
    private String saveDate;
    private String owner;


}
