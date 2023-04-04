package com.kappcore.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplexSearchDTO {
    private String title;
    private String body;
    private String tag;
    private String saveDate;
    private String owner;
}
