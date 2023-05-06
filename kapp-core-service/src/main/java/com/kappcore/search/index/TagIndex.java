package com.kappcore.search.index;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum TagIndex implements EnumAware {
    IT_NOTE("IT_NOTE", "it_note_index", "计算机笔记");

    private String tag;
    private String index;
    private String tip;

    TagIndex(String tag, String index, String tip) {
        this.tag = tag;
        this.index = index;
        this.tip = tip;
    }

    @Override
    public String getCode() {
        return this.index;
    }

    public static String getIndexByTag(String tag) {
        for (TagIndex value : values()) {
            if (Objects.equals(value.tag, tag)) {
                return value.index;
            }
        }
        throw new IllegalArgumentException("不存在的tag");
    }
}
