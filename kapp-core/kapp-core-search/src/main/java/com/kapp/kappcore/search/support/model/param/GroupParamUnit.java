package com.kapp.kappcore.search.support.model.param;

import com.kapp.kappcore.search.support.option.GroupOption;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Author:Heping
 * Date: 2024/6/26 17:27
 */
@Data
@AllArgsConstructor
public class GroupParamUnit {
    private String groupKey;
    private GroupOption groupOption;
}
