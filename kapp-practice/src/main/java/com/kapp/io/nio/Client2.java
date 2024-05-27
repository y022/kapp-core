package com.kapp.io.nio;

import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        NioClientImpl nioClient = new NioClientImpl("ad","nike", 10000);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            nioClient.send(next);
        }
    }
}
