package com.kapp.kappcore.task.order.task.container;

import com.kapp.kappcore.model.dto.share.task.TaskType;
import com.kapp.kappcore.model.entity.share.Task;
import com.kapp.kappcore.task.order.domain.reposity.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public List<Task> fetchTask(TaskType taskType, int count) {
        Task task = new Task();
        task.setTaskType(TaskType.SCHEDULED.name());
        Page<Task> result = repository.findAll(Example.of(task, ExampleMatcher.matching().withMatcher("task_type", ExampleMatcher.GenericPropertyMatchers.exact())), PageRequest.of(1, count));
        return result.toList();
    }

    @Override
    public void updateTask(List<Task> task) {
        repository.saveAll(task);
    }
}
