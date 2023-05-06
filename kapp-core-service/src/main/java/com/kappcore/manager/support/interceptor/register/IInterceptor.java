package com.kappcore.manager.support.interceptor.register;

import com.kappcore.manager.context.SearchContext;
import com.kappcore.manager.support.interceptor.SearchInterceptor;

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
