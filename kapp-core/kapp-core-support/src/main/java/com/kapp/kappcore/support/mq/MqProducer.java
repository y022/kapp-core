package com.kapp.kappcore.support.mq;

public interface MqProducer {
    void send(MqRouteMapping.Exchange exchange, MqRouteMapping.RoutingKey routingKey, Object messageObj);

    void send(String exchange, String routingKey, Object messageObj);
}
