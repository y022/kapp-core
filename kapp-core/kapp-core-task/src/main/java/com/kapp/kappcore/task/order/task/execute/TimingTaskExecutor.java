package com.kapp.kappcore.task.order.task.execute;

import com.kapp.kappcore.model.dto.share.task.TaskType;
import com.kapp.kappcore.model.entity.share.Task;
import com.kapp.kappcore.task.order.task.container.SimpleTaskContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:Heping
 * Date: 2024/6/11 16:22
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TimingTaskExecutor {
    private final SimpleTaskContainer taskContainer;

    @Scheduled(fixedDelay = 60 * 1000, initialDelay = 2000)
    public void execute() {
        log.info("timing task start...");
        List<Task> tasks = taskContainer.fetchTask(TaskType.SCHEDULED);
        for (Task task : tasks) {
            log.info(task.toString());
        }
    }

}
