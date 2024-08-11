package com.kapp;

import com.alibaba.ttl.TransmittableThreadLocal;

public class Main {

    private static final TransmittableThreadLocal t = new TransmittableThreadLocal();

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}