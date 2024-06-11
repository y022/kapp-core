package com.kapp.kappcore.task.order.task.container;

import com.kapp.kappcore.model.dto.share.task.TaskType;
import com.kapp.kappcore.model.entity.share.Task;
import com.kapp.kappcore.task.order.domain.reposity.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:Heping
 * Date: 2024/6/11 16:17
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleTaskContainer implements TaskContainer {
    private final TaskRepository repository;

    @Override
    public List<Task> fetchTask(TaskType taskType) {
        Task task = new Task();
        task.setTaskType(TaskType.SCHEDULED.name());
        return repository.findAll(Example.of(task,
                ExampleMatcher.matching()
                        .withMatcher("task_type", ExampleMatcher.GenericPropertyMatchers.exact())));
    }
}
