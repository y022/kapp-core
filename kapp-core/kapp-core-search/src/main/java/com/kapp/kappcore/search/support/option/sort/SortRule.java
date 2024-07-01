package com.kapp.kappcore.search.support.option.sort;

import lombok.Getter;

/**
 * Author:Heping
 * Date: 2024/6/24 22:21
 */
@Getter
public enum SortRule {
    SCORE("00", "分数"),
    FIELD("01", "字段"),
    ;
    private final String code;
    private final String name;

    SortRule(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public static SortRule codeOf(String sortRule) {
        for (SortRule value : SortRule.values()) {
            if (value.code.equals(sortRule)) {
                return value;
            }
        }
        return null;
    }
}
