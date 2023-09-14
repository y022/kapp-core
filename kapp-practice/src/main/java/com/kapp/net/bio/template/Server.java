package com.kapp.net.bio.template;

import com.kapp.net.bio.cons.NetCons;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {


    private BufferedReader reader;
    private ServerSocket server;
    private Socket socket;

    void getServer(){
        try {
            server=new ServerSocket(NetCons.port);        //实例化Socket对象
            System.out.println("服务器套接字已创建成功");

            while(true) {
                System.out.println("等待客户机的连接");
                socket=server.accept();           //accept()方法会返回一个和客户端Socket对象相连的Socket对象
                reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                getClientMessage();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //读取客户端发送过来的信息
    private void getClientMessage() {
        try {
            while(true) {
                //获得客户端信息
                System.out.println("客户机："+reader.readLine());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        try {
            if(reader!=null) {
                reader.close();
            }
            if(socket!=null) {
                socket.close();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Server tcp=new  Server();
        tcp.getServer();
    }

}
