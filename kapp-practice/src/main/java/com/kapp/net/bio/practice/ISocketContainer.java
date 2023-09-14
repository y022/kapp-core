package com.kapp.net.bio.practice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class ISocketContainer {
    protected Map<String, ISocket> socketMap = new HashMap<>();

   public   abstract   ISocket put(String key,ISocket socket);

    public abstract Map<String, ISocket>  socketMap();

    public   abstract  void remove(ISocket socket);

}
