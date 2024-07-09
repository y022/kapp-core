package com.kapp.kappcore.model.dto.book;

import lombok.Getter;

/**
 * Author:Heping
 * Date: 2024/7/9 17:59
 */
@Getter
public enum BookType {

    TXT("txt"), PDF("pdf"), EPUB("epub"), MOBI("mobi"),
    ;

    private final String type;


    BookType(String type) {
        this.type = type;
    }

    public static BookType typeOf(String format) {


        for (BookType value : BookType.values()) {
            if (value.type.equals(format)) {
                return value;
            }
        }
        return null;
    }
}
