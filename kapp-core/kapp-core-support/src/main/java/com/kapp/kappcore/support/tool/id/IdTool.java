package com.kapp.kappcore.support.tool.id;

import java.util.UUID;

public class IdTool {


    public static String getId() {
        UUID uuid = uuid();
        return uuid.toString().replaceAll("-", "");
    }

    public static String getCode() {
        UUID uuid = uuid();
        return "c_" + uuid.toString().replaceAll("-", "");
    }

    private static UUID uuid() {
        return UUID.randomUUID();
    }
}
