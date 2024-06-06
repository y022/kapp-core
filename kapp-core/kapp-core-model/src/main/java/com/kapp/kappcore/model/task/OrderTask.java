package com.kapp.kappcore.model.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTask implements Task {
    private String taskId;
    private String orderId;
    private String taskStartTime;
    private TimeDefinition timeDefinition;

    @Override
    public String taskId() {
        return getTaskId();
    }

    @Override
    public TimeDefinition timeDefinition() {
        return getTimeDefinition();
    }

}
