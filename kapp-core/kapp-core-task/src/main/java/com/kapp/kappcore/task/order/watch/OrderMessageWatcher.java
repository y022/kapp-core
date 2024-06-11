package com.kapp.kappcore.task.order.watch;

import com.kapp.kappcore.model.dto.share.message.impl.TimeKappMessage;
import com.kapp.kappcore.model.dto.share.task.TaskType;
import com.kapp.kappcore.model.entity.share.Task;
import com.kapp.kappcore.model.entity.share.WSOrder;
import com.kapp.kappcore.service.domain.repository.share.order.WSOrderRepository;
import com.kapp.kappcore.support.mq.MqRouteMapping;
import com.kapp.kappcore.support.mq.annotation.MqConsumer;
import com.kapp.kappcore.support.tool.date.DateTool;
import com.kapp.kappcore.task.order.domain.reposity.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Author:Heping
 * Date: 2024/6/6 20:41
 */
@Slf4j
@Component
public class OrderMessageWatcher extends AbstractMessageWatcher<TimeKappMessage> {

    private final WSOrderRepository wsOrderRepository;
    private final TaskRepository taskRepository;

    public OrderMessageWatcher(WSOrderRepository wsOrderRepository, TaskRepository taskRepository) {
        this.wsOrderRepository = wsOrderRepository;
        this.taskRepository = taskRepository;
    }

    /**
     * for order pay message,only save to table here.
     *
     * @param message message obj
     * @throws Exception
     */
    @Override
    @MqConsumer(queue = MqRouteMapping.Queue.ADD_ORDER)
    public void watch(TimeKappMessage message) throws Exception {
        super.processMessage(msg -> {
            String messageId = message.messageId();
            WSOrder ws = wsOrderRepository.findById(messageId).orElse(null);
            if (ws == null) {
                log.warn("order is empty,ws id : {}", messageId);
                return;
            }
            Task task = new Task();
            task.init(ws.getOrderId(), ws.getOrderId(), message.getDuration().toMillis(), 0,TaskType.SCHEDULED.name());
            String now = DateTool.now();
            task.time(now, now);
            taskRepository.save(task);
        }, message);

    }
}
