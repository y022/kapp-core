package com.kapp.kappcore.service.biz.note.search.support.convert;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class Converter {
    public static void convertHighlight(String highlightField, Map<String, Object> highlightContent, Class<?> clz, Object object) {
        Object content = highlightContent.get(highlightField);
        if (content != null) {
            Field field;
            try {
                field = clz.getDeclaredField(highlightField);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(true);
            if (field.getType().isArray()) {
                content = new String[]{content.toString()};
            }
            if (field.getType().isAssignableFrom(List.class)) {
                content = List.of(content);
            }
            try {
                field.set(object, content);
            } catch (IllegalAccessException ile) {
                //ignore
            }
        }
    }
}
