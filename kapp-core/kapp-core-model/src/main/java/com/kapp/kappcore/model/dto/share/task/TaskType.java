package com.kapp.kappcore.model.dto.share.task;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * Author:Heping
 * Date: 2024/6/11 15:02
 */
@Getter
public enum TaskType {

    SCHEDULED,;


    public static boolean designate(String taskType,TaskType typeEnum){
        return StringUtils.isNotBlank(taskType) && TaskType.valueOf(taskType).equals(typeEnum);
    }
}
