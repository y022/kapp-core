package com.kapp.kappcore.biz.note.search.core;

import com.kapp.kappcore.biz.note.search.context.SearchContext;
import org.elasticsearch.action.search.SearchResponse;

public interface SearchControl {
    /**
     * 检索开始之前
     *
     * @param context search context
     */
    void before(SearchContext context);

    /**
     * 检索完成之后
     *
     * @param context search response
     */
    void after(SearchResponse context);
}
