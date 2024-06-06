package com.kapp.kappcore.service.biz.share.message;

import com.kapp.kappcore.model.dto.share.message.TimeMessage;
import com.kapp.kappcore.support.mq.MessageCenter;
import com.kapp.kappcore.support.mq.MqProducer;
import com.kapp.kappcore.support.mq.MqRouteMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WSOrderMessageCenter implements MessageCenter<TimeMessage> {

    private final MqProducer mqProducer;
    private static final String DEFAULT_EXCHANGE = MqRouteMapping.Exchange.ORDER;
    private static final String DEFAULT_ROUTINGKEY = MqRouteMapping.RoutingKey.ADD_ORDER;

    public void addOrderMessage(TimeMessage timeMessage) {
        send(timeMessage);
    }

    @Override
    public void send(TimeMessage timeMessage) {
        send(DEFAULT_EXCHANGE, DEFAULT_ROUTINGKEY, timeMessage);
    }

    @Override
    public void send(String exchange, String routingKey, TimeMessage timeMessage) {
        mqProducer.send(exchange, routingKey, timeMessage);
    }
}
