package com.kapp.kappcore.search.support.option;

import lombok.Getter;

/**
 * Author:Heping
 * Date: 2024/6/23 17:05
 */
@Getter
public enum DocOption {
    SEARCH("00", "查询"), GROUP("01", "分组查询"),

    DELETE("10", "删除"), UPDATE("20", "更新"), ADD("99", "新增"), NONE("100", "未知类型"),
    ;


    private final String code;
    private final String desc;

    DocOption(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static DocOption codeOf(String code) {
        for (DocOption value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }


}
