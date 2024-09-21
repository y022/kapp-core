package com.kapp.io.net;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BioPracticeV2 {
    private static final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(4, 32, 20, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));

    static {
    }
//    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = new ServerSocket(9000);
//        while (true) {
//            Socket scoket;
//            try {
//                scoket = serverSocket.accept();
//                poolExecutor.execute(() -> {
//                    byte[] bytes = new byte[1024];
//                    try {
//                        int read = scoket.getInputStream().read(bytes);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//            } catch (IOException e) {
//                serverSocket.close();
//                throw new RuntimeException(e);
//            }
//        }
//    }


    public static void main(String[] args) {
        poolExecutor.execute(() -> {

        });
    }
}
