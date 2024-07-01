package com.kapp.kappcore.search.support.option;

import lombok.Getter;

/**
 * Author:Heping
 * Date: 2024/6/25 10:17
 */
@Getter
public enum MultiQueryRule {
    MUST("00", "必须"),
    MUST_NOT("01", "必须不"),
    SHOULD("03", "或者"),
    FILTER("04", "过滤"),

    ;

    private final String code;
    private final String desc;

    MultiQueryRule(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
