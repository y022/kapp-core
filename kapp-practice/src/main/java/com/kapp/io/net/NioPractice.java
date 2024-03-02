//package com.kapp.io;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.net.ServerSocket;
//import java.net.SocketImpl;
//import java.nio.ByteBuffer;
//import java.nio.channels.ServerSocketChannel;
//import java.nio.channels.SocketChannel;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//public class NioPractice {
//
//
//    private static final List<SocketChannel> socketChannels = new ArrayList<>();
//
//    public static void main(String[] args) throws IOException {
//        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 9000));
//        serverSocketChannel.configureBlocking(false);
//        new Runnable() {
//            @lombok.SneakyThrows
//            @Override
//            public void run() {
//                while (true) {
//                    SocketChannel channel = serverSocketChannel.accept();
//                    if (channel != null) {
//                        channel.configureBlocking(false);
//                        socketChannels.add(channel);
//                    }
//                }
//            }
//        }.run();
//        new Runnable() {
//            @lombok.SneakyThrows
//            @Override
//            public void run() {
//                Iterator<SocketChannel> iterator = socketChannels.iterator();
//                while (iterator.hasNext()) {
//                    ByteBuffer allocate = ByteBuffer.allocate(10);
//                    SocketChannel next = iterator.next();
//                    int read = next.read(allocate);
//                    if (read == 0) {
//                        iterator.remove();
//                    } else {
//                        System.out.println((allocate));
//                    }
//                }
//            }
//        }.run();
//
//    }
//
//}
