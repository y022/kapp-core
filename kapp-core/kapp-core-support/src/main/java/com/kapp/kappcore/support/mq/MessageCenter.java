package com.kapp.kappcore.support.mq;

import com.kapp.kappcore.model.dto.share.message.Message;

public interface MessageCenter<T extends Message> {
    void send(T t);
    void send(String exchange, String routingKey, T t);
}
