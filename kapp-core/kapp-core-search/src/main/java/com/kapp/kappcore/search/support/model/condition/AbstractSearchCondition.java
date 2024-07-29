package com.kapp.kappcore.search.support.model.condition;

import com.kapp.kappcore.search.configuration.SearchConfiguration;
import lombok.EqualsAndHashCode;

/**
 * Author:Heping
 * Date: 2024/6/24 22:52
 */

@EqualsAndHashCode(callSuper = true)
public abstract class AbstractSearchCondition extends AbstractCondition {
    protected SearchConfiguration.SearchField searchField;

    /**
     * 是否查询全部数据
     *
     * @return 结果
     */
    abstract boolean searchAll();

    abstract boolean hasMultiCondition();


}
