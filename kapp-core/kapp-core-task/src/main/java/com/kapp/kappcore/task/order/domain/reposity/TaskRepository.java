package com.kapp.kappcore.task.order.domain.reposity;

import com.kapp.kappcore.model.entity.share.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author:Heping
 * Date: 2024/6/7 16:46
 */
public interface TaskRepository extends JpaRepository<Task, String> {
}
