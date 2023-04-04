package com.kappcore.manager.intercept;

import com.kappcore.manager.context.SearchContext;
import com.kappcore.manager.model.SearchTip;

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
