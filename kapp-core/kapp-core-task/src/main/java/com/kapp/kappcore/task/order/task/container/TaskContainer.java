package com.kapp.kappcore.task.order.task.container;

import com.kapp.kappcore.model.dto.share.task.TaskType;

import java.util.List;

/**
 * Author:Heping
 * Date: 2024/6/11 16:10
 */
public interface TaskContainer<T> {
    List<T> fetchTask(TaskType taskType);
    void updateTask(List<T> ts);

}



