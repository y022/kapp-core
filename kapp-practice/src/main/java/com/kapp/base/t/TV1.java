package com.kapp.base.t;

import java.util.ArrayList;

public class TV1 {
    public static void main(String[] args) {
        ArrayList<?> coll = new ArrayList<>();

        String a = "";
        ThreadLocal<Integer> integerThreadLocal = ThreadLocal.withInitial(() -> 1);
    }
}
