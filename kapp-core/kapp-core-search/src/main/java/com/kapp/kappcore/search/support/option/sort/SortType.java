package com.kapp.kappcore.search.support.option.sort;

import lombok.Getter;

@Getter
public enum SortType {
    DESC("desc", "倒序"),
    ASC("asc", "升序"),

    RANDOM("random", "随机"),
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