package com.kapp.io.net.netty.protocol;

public interface ISerialization {
    <T> byte[] serialize(T obj);
    <T> T deSerialize(byte[] data,Class<T> clz);
}