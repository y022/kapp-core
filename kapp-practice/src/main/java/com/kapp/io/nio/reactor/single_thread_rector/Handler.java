package com.kapp.io.nio.reactor.single_thread_rector;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Author:Heping
 * Date: 2024/9/7 23:53
 */

public class Handler implements Runnable {

    private final SocketChannel clientSocketChannel;

    public Handler(SocketChannel clientSocketChannel) {
        this.clientSocketChannel = clientSocketChannel;
    }

    @Override
    public void run() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            // 读取数据，在Nio下，此处不会阻塞
            int read = clientSocketChannel.read(byteBuffer);
            if (read <= 0) {
                clientSocketChannel.close();
            } else {
                System.out.println(new String(byteBuffer.array()));
                byteBuffer.clear();
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
