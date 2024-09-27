package com.kapp.io.nio.reactor.single_thread_rector;

import java.io.IOException;

/**
 * Author:Heping
 * Date: 2024/9/7 23:54
 */
public class StartEndPoint {
    public static void main(String[] args) throws IOException {
        Thread reactorThread = new Thread(new Reactor(8080));
        reactorThread.start();
    }
}
