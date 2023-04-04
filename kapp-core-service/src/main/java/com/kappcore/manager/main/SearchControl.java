package com.kappcore.manager.main;

import com.kappcore.manager.context.SearchContext;

public interface SearchControl {
    /**
     * 检索开始之前
     *
     * @param context
     */
    void before(SearchContext context);

    /**
     * 检索完成之后
     *
     * @param searchContext
     */
    void after(SearchContext searchContext);
}
