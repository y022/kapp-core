package com.kapp.io.nio.reactor.single_thread_rector;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Author:Heping
 * Date: 2024/9/7 23:47
 */
public class Acceptor implements Runnable {
    private final Selector selector;
    private final ServerSocketChannel listenSocketChannel;

    public Acceptor(Selector selector, ServerSocketChannel listenSocketChannel) {
        this.selector = selector;
        this.listenSocketChannel = listenSocketChannel;
    }


    @Override
    public void run() {
        try {
            // 为连接的客户端创建client-socket管道
            SocketChannel clientSocketChannel = listenSocketChannel.accept();
            // 设置为非阻塞
            clientSocketChannel.configureBlocking(false);
            // READ事件的附加器是Handler
            clientSocketChannel.register(selector, SelectionKey.OP_READ,
                    new Handler(clientSocketChannel));
        } catch (IOException e) {
            System.err.println("Accept failed: " + e);
        }
    }
}
