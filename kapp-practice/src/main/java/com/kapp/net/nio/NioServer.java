package com.kapp.net.nio;

import com.kapp.net.bio.cons.NetCons;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
public class NioServer {
    public static void main(String[] args) throws IOException {
        //使用nio来理解阻塞模式,单线程
        ByteBuffer buffer =ByteBuffer.allocate(16);
        //1.创建了服务器
        ServerSocketChannel ssc=ServerSocketChannel.open();
        ssc.configureBlocking(false);//切换成非阻塞模式
        //2.绑定监听端口
        ssc.bind(new InetSocketAddress(NetCons.port));
        //3.连接集合
        List<SocketChannel> channels=new ArrayList<>();
        while(true){
            //4.accept建立与客户端连接,SocketChannel用来与客户端之间通信
            SocketChannel sc=ssc.accept();//非阻塞方法,线程运行
            if(sc!=null){
                sc.configureBlocking(false);
                channels.add(sc);
            }
            // log.debug("connecting...{}",sc);
            for(SocketChannel channel:channels){
                //5.接收客户端发送的数据
                int read=channel.read(buffer);//非阻塞，线程仍然会继续运行，如果没有读到数据，read返回0
                if(read>0){
                    buffer.flip();
                    byte[] bytes = new byte[read];
                    buffer.get(bytes);
                    System.out.println(new String(bytes));
                    buffer.clear();
                }
            }
        }
    }
}
