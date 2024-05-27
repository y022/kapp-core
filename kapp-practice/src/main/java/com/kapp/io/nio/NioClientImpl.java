package com.kapp.io.nio;

import com.kapp.base.log.KappLogVal;
import com.kapp.base.log.LogVal;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NioClientImpl implements NioClient {
    private final String ownId;
    private final String targetUserId;
    private SocketChannel channel;
    private static final LogVal log = new KappLogVal(NioClientImpl.class.getName());

    public NioClientImpl(String ownId, String targetUserId, int port) {
        this.targetUserId = targetUserId;
        this.ownId = ownId;
        if (!openSession(port)) {
            throw new RuntimeException("init connection failed");
        }
        new Thread(() -> {
            while (true) {
                try {
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int count = channel.read(buffer);
                    String content = new String(buffer.array(), 0, count, StandardCharsets.UTF_8);
                    buffer.clear();

                    String[] contentArray = content.split(":");
                    if (contentArray.length > 2) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 2; i < contentArray.length; i++) {
                            sb.append(contentArray[i]);
                        }
                        log.log(sb.toString());
                    }
                } catch (IOException e) {
                    //
                }
            }
        }).start();
    }

    @Override
    public boolean openSession(int port) {
        try {
            channel = SocketChannel.open();
            if (channel.connect(new InetSocketAddress(10000))) {
                send(ownId);
            }
            return true;
        } catch (IOException e) {
            log.logError("openSession error:", e);
            return false;
        }
    }

    @Override
    public void send(String data) {
        ByteBuffer buffer = ByteBuffer.wrap(buildMessage(data).getBytes(StandardCharsets.UTF_8));
        try {
            channel.write(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            buffer.clear();
        }
    }

    private String buildMessage(String message) {
        return ownId + ":" + targetUserId + ":" + message;
    }
}
