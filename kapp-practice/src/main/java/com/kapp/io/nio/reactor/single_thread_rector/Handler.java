package com.kapp.io.nio.reactor.single_thread_rector;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Author:Heping
 * Date: 2024/9/7 23:53
 */

public class Handler implements Runnable {
    private final ByteBuffer by = ByteBuffer.allocate(10240);
    private final SocketChannel clientSocketChannel;

    public Handler(SocketChannel clientSocketChannel) {
        this.clientSocketChannel = clientSocketChannel;
    }

    @Override
    public void run() {
        try {
            by.clear();
            // 读取数据，在Nio下，此处不会阻塞
            int read = clientSocketChannel.read(by);
            if (read < 0) {
                clientSocketChannel.close();
            } else if (read > 0) {
                by.flip();
                System.out.println(new String(by.array(), 0, read));
            }
        } catch (Throwable e1) {
            try {
                clientSocketChannel.close();
            } catch (Throwable e2) {
                System.err.println(e2);
            }
        }
    }
}
