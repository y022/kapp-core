package com.kapp.kappcore.task.order;

import com.kapp.kappcore.model.task.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultTaskCenter implements TaskCenter{



    @Override
    public List<Task> runningTasks() {

        return List.of();
    }
}
