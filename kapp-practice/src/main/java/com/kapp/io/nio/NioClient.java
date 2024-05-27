package com.kapp.io.nio;

public interface NioClient {
    boolean openSession(int port);
    void send(String data);
}
