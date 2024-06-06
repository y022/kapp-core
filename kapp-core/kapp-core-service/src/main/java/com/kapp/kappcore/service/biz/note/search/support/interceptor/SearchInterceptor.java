package com.kapp.kappcore.service.biz.note.search.support.interceptor;

import com.kapp.kappcore.service.biz.note.search.context.SearchContext;
import com.kapp.kappcore.service.biz.note.search.model.SearchTip;

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
