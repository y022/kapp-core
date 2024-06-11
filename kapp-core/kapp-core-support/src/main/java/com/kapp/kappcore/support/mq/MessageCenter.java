package com.kapp.kappcore.support.mq;

import com.kapp.kappcore.model.dto.share.message.KappMessage;

public interface MessageCenter<T extends KappMessage> {
    void send(T t);
    void send(String exchange, String routingKey, T t);
}
