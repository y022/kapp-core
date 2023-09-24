package com.kapp.kappcore.search.support.interceptor;

import com.kapp.kappcore.search.context.SearchContext;
import com.kapp.kappcore.search.model.SearchTip;

//@Component
public class ParamInterceptor implements SearchInterceptor {

    @Override
    public SearchTip intercept(SearchContext context) {
        return nonNull(context);
    }

    SearchTip nonNull(SearchContext context) {
        if (context == null || context.target() == null) {
            return SearchTip.nullTip();
        }
        return null;
    }
}
