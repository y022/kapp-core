package com.kapp.kappcore.service.biz.note;

import com.kapp.kappcore.service.biz.note.search.context.SearchContext;
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
     * @param response search response
     */
    void after(SearchResponse response);
}
