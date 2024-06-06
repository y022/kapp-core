package com.kapp.kappcore.support.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class KappMqProducer implements MqProducer, InitializingBean {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper obm = new ObjectMapper();

    @Override
    public void afterPropertiesSet() {
        rabbitTemplate.setConfirmCallback((correlation, ack, cause) -> {
            if (ack) {
                log.info("message send ok! correlationData Id :{}", correlation.getId());
            } else {
                log.error("message send error! correlationData Id :{} ,cause : {}", correlation.getId(), cause);
            }
        });
    }

    @Override
    public void send(MqRouteMapping.Exchange exchange, MqRouteMapping.RoutingKey routingKey, Object messageObj) {
        send(exchange.toString(),routingKey.toString(),messageObj);
    }

    public void send(String exchange, String routingKey, Object messageObj) {
        Message message = toMessage(messageObj, new MessageProperties());
        send(exchange, routingKey, message, toCorrelationData(message));
    }

    private void send(String exchange, String routingKey, Message message, CorrelationData correlationData) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }

    private CorrelationData toCorrelationData(Message message) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        correlationData.setReturnedMessage(message);
        return correlationData;
    }

    private Message toMessage(Object o, MessageProperties messageProperties) {
        try {
            String data = obm.writeValueAsString(o);
            return new Message(data.getBytes(StandardCharsets.UTF_8), messageProperties);
        } catch (JsonProcessingException e) {
            log.error("message build error", e);
            throw new RuntimeException(e);
        }
    }



}
