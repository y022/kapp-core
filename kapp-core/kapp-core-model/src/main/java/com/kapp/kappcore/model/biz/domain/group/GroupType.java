package com.kapp.kappcore.model.biz.domain.group;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GroupType {
    COUNT("0", "count"),
    SHOW("1", "show"),
    ;


    private String code;
    private String message;
}
