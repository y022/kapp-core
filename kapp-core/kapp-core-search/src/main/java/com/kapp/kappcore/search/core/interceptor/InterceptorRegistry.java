package com.kapp.kappcore.search.core.interceptor;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.support.model.param.SearchParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Heping
 * Date: 2024/6/25 14:11
 */
public class InterceptorRegistry {
    private final List<SearchInterceptor> interceptors;

    public InterceptorRegistry(List<SearchInterceptor> interceptors) {
        if (interceptors != null && !interceptors.isEmpty()) {
            this.interceptors = interceptors;
        } else {
            this.interceptors = new ArrayList<>(0);
        }
    }

    public void invoke(SearchParam searchParam) throws SearchException {
        if (interceptors != null) {
            for (SearchInterceptor interceptor : interceptors) {
                interceptor.intercept(searchParam);
            }
        }
    }
}
