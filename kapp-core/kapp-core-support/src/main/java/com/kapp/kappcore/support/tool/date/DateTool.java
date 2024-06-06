package com.kapp.kappcore.support.tool.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTool {
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");

    public static String now() {
        return LocalDateTime.now().format(DF);
    }
}
