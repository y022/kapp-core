package com.kapp.kappcore.model.entity.share;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Author:Heping
 * Date: 2024/6/7 16:49
 */
@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryBean {
    @Column(length = 32)
    protected String createTime;
    @Column(length = 32)
    protected String updateTime;

    public void updateTime(String createTime, String updateTime) {
        if (createTime != null && !createTime.isEmpty()) {
            setCreateTime(createTime);
        }
        if (updateTime != null && !updateTime.isEmpty()) {
            setUpdateTime(updateTime);
        }
    }

    public void updateTime(String updateTime) {
        updateTime(null, updateTime);
    }
}
