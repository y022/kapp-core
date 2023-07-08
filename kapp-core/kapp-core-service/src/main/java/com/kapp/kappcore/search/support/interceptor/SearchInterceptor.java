package com.kapp.kappcore.search.support.interceptor;

import com.kapp.kappcore.search.model.SearchTip;
import com.kapp.kappcore.search.context.SearchContext;

/**
 * 检索拦截器
 */
public interface SearchInterceptor {

    SearchTip intercept(SearchContext context);

    default SearchTip nonNull(SearchContext context) {
        if (context == null || context.target() == null) {
            return SearchTip.nullTip();
        }
        return null;
    }


}
