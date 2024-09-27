package com.kapp.io.nio.reactor.single_thread_rector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/9/7 23:42
 */

public class Reactor implements Runnable {

    private final Selector selector;

    /**
     * 实例化selector
     *
     * @param port 端口
     * @throws IOException IO异常
     */
    public Reactor(int port) throws IOException {
        assert port > 0;

        selector = Selector.open();
        ServerSocketChannel listenSocketChannel = ServerSocketChannel.open();

        listenSocketChannel.socket().bind(new InetSocketAddress(port));

        listenSocketChannel.configureBlocking(false);
        //服务端SocketChannel只关注连接事件。
        listenSocketChannel.register(selector, SelectionKey.OP_ACCEPT,
                new Acceptor(selector, listenSocketChannel));
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                // 获取发生的事件，此处会阻塞
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterable = selectionKeys.iterator();
                while (iterable.hasNext()) {
                    eventDispatch(iterable.next());
                    iterable.remove();
                }
            } catch (IOException e) {
                System.err.println("reactor failed:" + e);
            }
        }
    }

    /**
     * 所有连接事件交由注册到服务端socketChannel的Acceptor来处理
     *
     * @param selectionKey key
     */
    private void eventDispatch(SelectionKey selectionKey) {
        Runnable attachment = (Runnable) selectionKey.attachment();
        if (attachment != null) {
            attachment.run();
        }
    }
}
