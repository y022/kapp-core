package com.kapp;

public class Main {
    private static final int _1MB=1024*1024;


    public static void main(String[] args) {
        System.out.println("Hello world!");

        byte[] allocation_1=new byte[2*_1MB];
        byte[] allocation_2=new byte[2*_1MB];
        byte[] allocation_3=new byte[2*_1MB];
        byte[] allocation_4=new byte[4*_1MB];

    }
}