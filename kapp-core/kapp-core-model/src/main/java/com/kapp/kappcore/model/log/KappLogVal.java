package com.kapp.kappcore.model.log;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class KappLogVal implements LogVal {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private static final Properties PROPERTIES = new Properties();
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final PrintWriter PRINT_WRITER = new PrintWriter(new OutputStreamWriter(System.out), true);

    public static KappLogVal getLog(String name) {
        return new KappLogVal(name);
    }

    static {
        PROPERTIES.put("Date", LocalDateTime.now().format(DF));
        PROPERTIES.put("Tid", Thread.currentThread().getId());
    }

    public KappLogVal(String loggerName) {
        PROPERTIES.putIfAbsent("Name", loggerName);
        String envHost = System.getenv("kapp.host");
        PROPERTIES.putIfAbsent("Env-Host", getEnv("kapp.host"));
    }

    @Override
    public Properties val() {
        return PROPERTIES;
    }

    @Override
    public void log(Object o) {
        String logMsg = buildMsg(o, ANSI_GREEN);
        try {
            PRINT_WRITER.println(logMsg);
        } catch (Exception e) {

        }
    }

    @Override
    public void logError(Object o, Throwable e) {
        String logMsg = buildMsg(o, ANSI_RED);
        PRINT_WRITER.println(logMsg);
        e.printStackTrace(PRINT_WRITER);
    }

    private String buildMsg(Object object, String color) {
        updateTime();
        StringBuilder sb = new StringBuilder();
        sb.append(color);
        PROPERTIES.forEach((k, v) -> sb.append(k).append(" ").append(v).append("|"));
        sb.append(object.toString());
        return sb.toString();
    }

    private void updateTime() {
        PROPERTIES.computeIfPresent("Date", (v1, v2) -> LocalDateTime.now().format(DF));
    }

    private String getEnv(String envName) {
        String property = System.getProperty(envName);
        return property == null ? "" : property;
    }
}
