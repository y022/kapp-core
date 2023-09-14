package com.kapp.net.bio;

import com.kapp.net.bio.cons.NetCons;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class IClient2 {


    public static void main(String[] args) throws IOException {

        Socket clientSocket = new Socket(NetCons.ip, NetCons.port);

        Scanner scanner = new Scanner(System.in);
        while (true){
            String next = scanner.next();
            clientSocket.getOutputStream().write(next.getBytes());
        }

    }
}
