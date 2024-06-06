package com.kapp.kappcore.task.order;

import com.kapp.kappcore.model.dto.share.message.TimeMessage;
import com.kapp.kappcore.model.entity.share.WSOrder;
import com.kapp.kappcore.model.task.OrderTask;
import com.kapp.kappcore.model.task.TimeDefinition;
import com.kapp.kappcore.service.domain.repository.share.order.WSOrderRepository;
import com.kapp.kappcore.support.mq.MqRouteMapping;
import com.kapp.kappcore.support.mq.annotation.MqConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMessageHandler {
    private final WSOrderRepository wsOrderRepository;

    @MqConsumer(queue = MqRouteMapping.Queue.ADD_ORDER)
    public void save(TimeMessage message) {
        if (message == null) {
            return;
        }
        String messageId = message.getId();
        int effectiveTime = message.getEffectiveTime();
        TimeUnit unit = message.getUnit();
        Optional<WSOrder> wsOrder = wsOrderRepository.findById(messageId);
        if (wsOrder.isEmpty()) {
            log.warn("order is empty,ws id : {}", messageId);
            return;
        }
        WSOrder ws = wsOrder.get();
        OrderTask orderTask = new OrderTask();
        orderTask.setOrderId(ws.getOrderId());
        orderTask.setTaskStartTime(ws.getCreateTime());
        orderTask.setTaskId(messageId);

        TimeDefinition timeDefinition = new TimeDefinition();
        timeDefinition.setInterval(message.getEffectiveTime());
        timeDefinition.setUnit(message.getUnit());
    }
}



