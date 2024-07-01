package com.kapp.kappcore.search.support.option;

import lombok.Getter;

/**
 * Author:Heping
 * Date: 2024/6/23 16:51
 */
@Getter
public enum ContHitStrategy {
    ACCURATE("00", "精确查询"),
    PARTICIPLE("01", "分词查询"),
    WILDCARD("02", "通配符"),
    FUZZY("03", "模糊"),
    RANGE("03", "区间"),
    ;
    private final String code;
    private final String desc;

    ContHitStrategy(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ContHitStrategy codeOf(String code) {
        for (ContHitStrategy value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
