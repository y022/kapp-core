package com.kapp.kappcore.search.support.option;

import lombok.Getter;

/**
 * Author:Heping
 * Date: 2024/6/26 16:41
 */
@Getter
public enum GroupOption {
    //Metric
    COUNT("10", "_Count"),
    AVG("20", "_Avg"),
    MAX("30", "_Max"),
    MIN("40", "_Min"),
    SUM("50", "_Sum"),
    STATS("60", "_Stats"),
    //Bucket
    BUCKET("100", "_Bucket"),
    ;

    private final String code;
    private final String desc;

    GroupOption(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static GroupOption codeOf(String code) {
        for (GroupOption value : GroupOption.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
