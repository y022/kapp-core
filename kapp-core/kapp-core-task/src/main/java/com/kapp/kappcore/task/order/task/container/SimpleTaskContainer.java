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
public class SimpleTaskContainer implements TaskContainer<Task> {
    private final TaskRepository repository;

    @Override
    public List<Task> fetchTask(TaskType taskType) {
        return repository.findAll(Example.of(new Task() {{
                                                 setTaskType(TaskType.SCHEDULED.name());
                                             }},
                ExampleMatcher.matching().withMatcher("task_type", ExampleMatcher.GenericPropertyMatchers.exact())));
    }

    @Override
    public void updateTask(List<Task> task) {
        repository.saveAll(task);
    }
}
