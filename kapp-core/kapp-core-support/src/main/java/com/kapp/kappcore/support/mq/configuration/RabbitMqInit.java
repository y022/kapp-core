package com.kapp.kappcore.support.mq.configuration;

import com.kapp.kappcore.support.mq.MqRouteMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class RabbitMqInit {

    public RabbitMqInit(RabbitAdmin rabbitAdmin) {
        log.info("trying init mq configuration......");
        this.init(rabbitAdmin);
        log.info("init mq configuration complete!");
    }

    /**
     * init queue mapping
     * @param rabbitAdmin ad
     */
    private void init(RabbitAdmin rabbitAdmin) {
        Map<String, Exchange> exchangeMap = new HashMap<>(16);
        for (MqRouteMapping.Mapping value : MqRouteMapping.Mapping.values()) {
            Exchange exchange = Optional.ofNullable(exchangeMap.get(value.getExchange())).orElseGet(() -> {
                DirectExchange directExchange = new DirectExchange(value.getExchange(), true, false);
                exchangeMap.putIfAbsent(value.getExchange(), directExchange);
                return directExchange;
            });
            rabbitAdmin.declareExchange(exchange);
            Queue queue = new Queue(value.getQueue(), true, false, false);
            rabbitAdmin.declareQueue(queue);
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(value.getRoutingKey()).noargs();
            rabbitAdmin.declareBinding(binding);
        }
    }

}
