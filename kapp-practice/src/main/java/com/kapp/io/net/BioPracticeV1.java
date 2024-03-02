package com.kapp.io.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BioPracticeV1 {

    private static final List<Socket> sockets = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);

        while (true) {
            Socket sc;
            try {
                sc = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            sockets.add(sc);


            Iterator<Socket> iterator = sockets.iterator();

            Socket socket = iterator.next();
            byte[] bytes = new byte[1024];
            try {
                socket.getInputStream().read(bytes);
                System.out.println(new String(bytes));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

