package com.kapp.kappcore.service.biz.share.message;

import com.kapp.kappcore.model.dto.share.message.impl.TimeKappMessage;
import com.kapp.kappcore.support.mq.MessageCenter;
import com.kapp.kappcore.support.mq.MqProducer;
import com.kapp.kappcore.support.mq.MqRouteMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WSOrderMessageCenter implements MessageCenter<TimeKappMessage> {

    private final MqProducer mqProducer;
    private static final String DEFAULT_EXCHANGE = MqRouteMapping.Exchange.ORDER;
    private static final String DEFAULT_ROUTINGKEY = MqRouteMapping.RoutingKey.ADD_ORDER;

    public void addOrderMessage(TimeKappMessage timeMessage) {
        send(timeMessage);
    }

    @Override
    public void send(TimeKappMessage timeMessage) {
        send(DEFAULT_EXCHANGE, DEFAULT_ROUTINGKEY, timeMessage);
    }

    @Override
    public void send(String exchange, String routingKey, TimeKappMessage timeMessage) {
        mqProducer.send(exchange, routingKey, timeMessage);
    }
}
