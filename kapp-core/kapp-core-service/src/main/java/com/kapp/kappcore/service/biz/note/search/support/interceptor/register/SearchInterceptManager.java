package com.kapp.kappcore.service.biz.note.search.support.interceptor.register;


import com.kapp.kappcore.service.biz.note.search.context.SearchContext;
import com.kapp.kappcore.service.biz.note.search.support.interceptor.SearchInterceptor;

import java.util.Set;

public interface SearchInterceptManager {
    /**
     * 将返回一个不可操作的拦截器集合
     *
     * @return
     */
    Set<SearchInterceptor> getInterceptor();

    /**
     * 执行拦截
     *
     * @param searchContext
     */
    void doIntercept(SearchContext searchContext) throws Exception;
}
