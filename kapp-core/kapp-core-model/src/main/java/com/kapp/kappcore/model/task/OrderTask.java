package com.kapp.kappcore.model.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTask {
    /**
     * 任务Id
     */
    private String taskId;
    private String bizId;
    /**
     * millis 单位毫秒,存储两个时间点的差值
     */
    private Long expireTime;
    private String createTime;
    private String updateTime;


}
