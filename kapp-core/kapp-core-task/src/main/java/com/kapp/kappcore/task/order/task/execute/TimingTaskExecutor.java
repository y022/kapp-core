package com.kapp.kappcore.task.order.task.execute;

import com.kapp.kappcore.model.dto.share.pay.PayStatus;
import com.kapp.kappcore.model.dto.share.task.TaskType;
import com.kapp.kappcore.model.entity.share.Task;
import com.kapp.kappcore.model.entity.share.WSOrder;
import com.kapp.kappcore.service.domain.repository.share.order.WSOrderRepository;
import com.kapp.kappcore.support.tool.date.DateTool;
import com.kapp.kappcore.task.order.task.container.SimpleTaskContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * Author:Heping
 * Date: 2024/6/11 16:22
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TimingTaskExecutor {
    private final SimpleTaskContainer taskContainer;
    private final WSOrderRepository wsOrderRepository;

    @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 10000)
    public void execute() {
        log.info("timing task start...");
        List<Task> tasks = taskContainer.fetchTask(TaskType.SCHEDULED, 200);
        try {
            for (Task task : tasks) {
                String taskType = task.getTaskType();
                if (TaskType.designate(taskType, TaskType.SCHEDULED)) {
                    Optional<WSOrder> optionalWSOrder = wsOrderRepository.findById(task.getBizId());
                    if (optionalWSOrder.isPresent()) {
                        if (DateTool.expire(task.getCreateTime(), Duration.of(task.getExpireTime(), ChronoUnit.MILLIS))) {
                            WSOrder wsOrder = optionalWSOrder.get();
                            wsOrder.updateStatus(PayStatus.TIME_OUT.getCode(), DateTool.now());
                            wsOrderRepository.save(wsOrder);
                        }
                    }
                }
                task.incrScanTimes(DateTool.now());
            }
            taskContainer.updateTask(tasks);
        } catch (Exception e) {
            log.error("task handle error", e);
        }
    }
}
