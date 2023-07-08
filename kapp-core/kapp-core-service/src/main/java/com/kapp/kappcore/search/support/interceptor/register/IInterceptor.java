package com.kapp.kappcore.search.support.interceptor.register;

import com.kapp.kappcore.search.context.SearchContext;
import com.kapp.kappcore.search.support.interceptor.SearchInterceptor;

import java.util.Set;

public interface IInterceptor {
    /**
     * 将返回一个不可操作的拦截器集合
     *
     * @return
     */
    Set<SearchInterceptor> getInterceptor();

    /**
     * 执行拦截
     * @param searchContext
     */
    void  intercept(SearchContext searchContext);
}
