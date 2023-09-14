package com.kapp.net.bio.practice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.Socket;


@Data
@AllArgsConstructor
public class ISocket {
    private String key;
    private Socket socket;

}
