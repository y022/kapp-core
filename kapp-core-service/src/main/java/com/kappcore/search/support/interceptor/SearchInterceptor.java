package com.kappcore.search.support.interceptor;

import com.kappcore.search.context.SearchContext;
import com.kappcore.search.model.SearchTip;

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
