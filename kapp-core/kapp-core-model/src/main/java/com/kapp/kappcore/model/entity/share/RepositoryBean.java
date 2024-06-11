package com.kapp.kappcore.model.entity.share;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * Author:Heping
 * Date: 2024/6/7 16:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryBean {
    @Column(length = 32)
    protected String createTime;
    @Column(length = 32)
    protected String updateTime;

    public void time(String createTime, String updateTime) {
        if (createTime != null && !createTime.isEmpty()) {
            setCreateTime(createTime);
        }
        if (updateTime != null && !updateTime.isEmpty()) {
            setUpdateTime(updateTime);
        }
    }
}
