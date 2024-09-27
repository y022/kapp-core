package com.kapp.io.nio;

import com.kapp.base.log.KappLogVal;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class NioServerImpl implements NioServer {
    private static final KappLogVal log = new KappLogVal(NioServerImpl.class.getName());
    public final Selector selector;
    private final ServerSocketChannel serverSocketChannel;
    private static final Map<String, SocketChannel> SELECTION_MAP = new ConcurrentHashMap<>(256);

    public NioServerImpl(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", port)).register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new RuntimeException("open new Selector error!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<SelectionKey> selectionKeys() {
        return selector.selectedKeys();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        new Thread(() -> {
            while (true) {
                try {
                    if (selector.select() == 0) continue;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Set<SelectionKey> keys = selectionKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        try {
                            registerConn();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (key.isReadable()) {
                        try {
                            transmitMessage(key);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }


                }
            }
        }).start();
    }

    /**
     * 客服端连接事件处理
     *
     * @throws IOException
     */
    private void registerConn() throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        log.log("connection accepted,connector address: " + socketChannel.getRemoteAddress());
    }

    private void transmitMessage(SelectionKey key) throws IOException {
        synchronized (this) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer read = null;
            ByteBuffer write = null;
            try {
                read = ByteBuffer.allocate(1024);
                int bytesRead = socketChannel.read(read);
                if (bytesRead == 0) return;
                String message = new String(read.array(), 0, bytesRead, StandardCharsets.UTF_8);
                String ownId = getId(message, 0);
                if (ownId != null) {
                    SELECTION_MAP.putIfAbsent(ownId, socketChannel);
                } else {
                    return;
                }
                String targetUserId = getId(message, 1);
                write = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
                SocketChannel sc = SELECTION_MAP.get(targetUserId);
                if (sc != null) {
                    sc.write(write);
                }
            } catch (IOException e) {
                log.logError("transmit message error!", e);
            } finally {
                if (read != null) read.clear();
                if (write != null) write.clear();
            }
        }
    }

    private String getId(String message, int index) {
        if (message == null || message.isBlank()) {
            return null;
        }
        if (message.contains(":")) {
            return message.split(":")[index];
        }
        return null;
    }
}
