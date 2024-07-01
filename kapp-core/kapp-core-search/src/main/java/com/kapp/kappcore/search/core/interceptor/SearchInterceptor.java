package com.kapp.kappcore.search.core.interceptor;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.support.model.param.SearchParam;

/**
 * Author:Heping
 * Date: 2024/6/25 13:25
 */
public interface SearchInterceptor {
    int order();
    void intercept(SearchParam searchParam) throws SearchException;
}
