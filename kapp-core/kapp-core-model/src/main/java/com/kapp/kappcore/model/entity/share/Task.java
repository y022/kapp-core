package com.kapp.kappcore.model.entity.share;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author:Heping
 * Date: 2024/6/7 16:18
 */
@Data
@Entity
@Table(name = "tb_task_info", schema = "work_station")
@EqualsAndHashCode(callSuper = true)
public class Task extends RepositoryBean {
    /**
     * 任务Id
     */
    @Id
    @Column(length = 64)
    private String taskId;
    /**
     * 绑定的订单id
     */
    @Column(length = 64)
    private String bizId;
    /**
     * millis 单位毫秒,存储两个时间点的差值
     */
    private Long expireTime;
    /**
     * 扫描次数
     */
    @Column(length = 16)
    private Integer scanTimes;
    /**
     * 任务类型
     */
    @Column(length = 64)
    private String taskType;

    public void init(String taskId, String bizId, Long expireTime, Integer scanTimes,String taskType) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setBizId(bizId);
        task.setExpireTime(expireTime);
        task.setScanTimes(scanTimes);
        setTaskType(taskType);
    }

}
