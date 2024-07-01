package com.kapp.kappcore.search.support.option.sort;

import lombok.Getter;

@Getter
public enum SortType {
    DESC("00", "desc"),
    ASC("01", "asc"),

    RANDOM("99", "随机"),
    ;
    private final String code;
    private final String name;

    SortType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SortType codeOf(String sortType) {

        for (SortType value : SortType.values()) {
            if (value.code.equals(sortType)) {
                return value;
            }
        }
        return null;
    }
}