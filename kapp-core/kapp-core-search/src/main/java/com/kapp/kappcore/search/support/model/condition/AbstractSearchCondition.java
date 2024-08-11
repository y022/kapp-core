package com.kapp.kappcore.search.support.model.condition;

import com.kapp.kappcore.search.configuration.SearchConfiguration;
import com.kapp.kappcore.search.support.option.DocOption;
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

    public static ScrollCondition scroll(String index) {
        return new ScrollCondition(index, DocOption.SEARCH);
    }

    @EqualsAndHashCode(callSuper = true)
    public static class ScrollCondition extends AbstractSearchCondition {

        public ScrollCondition(String index, DocOption docOption) {
            this.index = index;
            this.option = docOption;
        }

        @Override
        boolean searchAll() {
            return false;
        }

        @Override
        boolean hasMultiCondition() {
            return false;
        }

        @Override
        public String read() {
            return "";
        }
    }
}
