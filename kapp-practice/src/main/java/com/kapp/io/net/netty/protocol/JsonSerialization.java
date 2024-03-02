package com.kapp.io.net.netty.protocol;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonSerialization implements ISerialization {

    private ObjectMapper ob;

    public JsonSerialization(){
        this.ob = new ObjectMapper();
    }


    @Override
    public <T> byte[] serialize(T obj) {
        try {
            return ob.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T deSerialize(byte[] data, Class<T> clz) {
        try {
            return ob.readValue(data,clz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}