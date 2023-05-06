package com.kappcore.search.support.interceptor.register;

import com.kappcore.search.context.SearchContext;
import com.kappcore.search.support.interceptor.SearchInterceptor;
import com.kappcore.search.model.SearchTip;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DefaultSearchIInterceptor implements IInterceptor {

    private Set<SearchInterceptor> interceptors = new HashSet<>(12);

    public DefaultSearchIInterceptor(@Nullable SearchInterceptor... existedInterceptors) {
        if (existedInterceptors != null) CollectionUtils.mergeArrayIntoCollection(existedInterceptors, interceptors);
    }


    @Override
    public Set<SearchInterceptor> getInterceptor() {
        return interceptors.stream().collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public void intercept(SearchContext searchContext) {
        for (SearchInterceptor interceptor : interceptors) {
            SearchTip tip = interceptor.intercept(searchContext);
            if (tip.exception()) {
                throw new IllegalArgumentException(tip.getTip());
            }
        }
    }


}
