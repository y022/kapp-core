package com.kapp.io.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Set;

public interface NioServer {

    /**
     * 返回已经建立的 selection key
     *
     * @return
     */
    Set<SelectionKey> selectionKeys();

    /**
     * 开始处理
     *
     * @throws IOException
     */
    void run() throws IOException;

}
