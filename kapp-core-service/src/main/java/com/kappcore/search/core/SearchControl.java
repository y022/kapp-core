package com.kappcore.search.core;

import com.kappcore.search.context.SearchContext;

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
