package com.kapp.net.bio.practice;

import java.util.Map;


public class DefaultISocketContainer extends ISocketContainer {

    @Override
    public ISocket put(String key, ISocket socket) {
        if (!socketMap.containsKey(key)) {
            System.out.println("receive new socket connect!key:" + key);
        }
        super.socketMap.putIfAbsent(key, socket);
        return socket;
    }

    @Override
    public Map<String, ISocket> socketMap() {
        return socketMap;
    }

    @Override
    public void remove(ISocket socket) {
        super.socketMap.remove(socket.getKey());
    }


    public void loop() {


    }
}
