package com.kapp.net.bio;

import com.kapp.net.bio.cons.NetCons;
import com.kapp.net.bio.practice.DefaultISocketContainer;
import com.kapp.net.bio.practice.ISocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IServer {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        DefaultISocketContainer socketContainer = new DefaultISocketContainer();

        ServerSocket serverSocket = new ServerSocket(NetCons.port);

        Runnable acceptTask = () -> {
            while (true) {
                Socket newSocket = null;
                try {
                    newSocket = serverSocket.accept();
                    //由于Socket的read方法时阻塞的,这里设置read的超时时间，防止read阻塞导致线程挂起
                    newSocket.setSoTimeout(1000);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String key = String.valueOf(newSocket.hashCode());
                socketContainer.put(key, new ISocket(key, newSocket));
            }
        };

        Runnable listenTask = () -> {
            while (true) {
                Map<String, ISocket> socketMap = socketContainer.socketMap();
                for (Map.Entry<String, ISocket> iSocketEntry : socketMap.entrySet()) {
                    byte[] bytes = new byte[1024];
                    try {
                        iSocketEntry.getValue().getSocket().getInputStream().read(bytes);
                        System.out.println(iSocketEntry.getKey() + " send message:" + new String(bytes));
                    } catch (IOException e) {
                        System.out.println(e.getCause());
                    }
                }
            }
        };
        executorService.submit(listenTask);

        executorService.submit(acceptTask);

    }

}
