package com.kapp.io.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

class NioServerImplTest {

    @Test
    void run() throws IOException {
        NioServerImpl nioServer = new NioServerImpl(10000);
        nioServer.run();
        SocketChannel open = SocketChannel.open();
        open.connect(new InetSocketAddress(10000));
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            ByteBuffer wrap = ByteBuffer.wrap(next.getBytes());
            open.write(wrap);
            wrap.clear();
        }
    }

    public static void main(String[] args) throws IOException {
        NioServerImpl nioServer = new NioServerImpl(10000);
        nioServer.run();
        SocketChannel open = SocketChannel.open();
        open.connect(new InetSocketAddress(10000));
        if (open.isConnected()) {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String next = scanner.next();
                ByteBuffer wrap = ByteBuffer.wrap(next.getBytes());
                open.write(wrap);
                wrap.clear();
            }
        }
    }
}