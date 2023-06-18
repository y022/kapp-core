package com.kapp.kappcore.search.support.interceptor;

import com.kapp.kappcore.search.context.SearchContext;
import com.kapp.kappcore.search.model.SearchTip;
import org.springframework.stereotype.Component;

//@Component
public class ParamInterceptor implements SearchInterceptor {

    @Override
    public SearchTip intercept(SearchContext context) {
        SearchTip searchTip = nonNull(context);
        if (searchTip != null) {
            return searchTip;
        }
        return null;
    }
}
