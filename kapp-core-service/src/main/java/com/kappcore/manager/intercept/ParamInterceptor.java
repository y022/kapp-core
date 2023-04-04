package com.kappcore.manager.intercept;

import com.kappcore.manager.context.SearchContext;
import com.kappcore.manager.model.SearchTip;
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
