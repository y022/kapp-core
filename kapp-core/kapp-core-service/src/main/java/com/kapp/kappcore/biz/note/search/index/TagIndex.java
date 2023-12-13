package com.kapp.kappcore.biz.note.search.index;

import com.kapp.kappcore.constant.ExCode;
import com.kapp.kappcore.exception.SearchException;
import lombok.Getter;

import java.util.Objects;

@Getter
public enum TagIndex implements EnumAware {
    IT_NOTE("IT_NOTE", "it_note_index", "计算机笔记"),
    KNOWLEDGE("KNOWLEDGE", "knowledge", "知识"),


    ;

    private final String tag;
    private final String index;
    private final String tip;

    TagIndex(String tag, String index, String tip) {
        this.tag = tag;
        this.index = index;
        this.tip = tip;
    }

    @Override
    public String getCode() {
        return this.index;
    }

    public static String getIndex(String tag) {
        for (TagIndex value : values()) {
            if (Objects.equals(value.tag, tag)) {
                return value.index;
            }
        }
        throw new SearchException(ExCode.index_error, "不存在的tag!");
    }

    public static String getTag(String index) {
        for (TagIndex value : values()) {
            if (Objects.equals(value.index, index)) {
                return value.getTag();
            }
        }
        return null;
    }
}
