package com.kappcore.search.support.interceptor;

import com.kappcore.search.context.SearchContext;
import com.kappcore.search.model.SearchTip;
import org.springframework.stereotype.Component;

@Component
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
