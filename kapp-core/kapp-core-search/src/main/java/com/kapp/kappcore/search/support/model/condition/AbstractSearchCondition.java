package com.kapp.kappcore.search.support.model.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author:Heping
 * Date: 2024/6/24 22:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractSearchCondition extends AbstractCondition {
    /**
     * 是否查询全部数据
     *
     * @return 结果
     */
    abstract boolean searchAll();

}
