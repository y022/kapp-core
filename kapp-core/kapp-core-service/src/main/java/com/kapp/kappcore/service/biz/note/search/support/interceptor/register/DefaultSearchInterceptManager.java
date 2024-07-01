package com.kapp.kappcore.service.biz.note.search.support.interceptor.register;

import com.kapp.kappcore.service.biz.note.search.context.SearchContext;
import com.kapp.kappcore.service.biz.note.search.support.interceptor.SearchInterceptor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component
public class DefaultSearchInterceptManager implements SearchInterceptManager {
    /**
     * 是否开启拦截
     */
    private static final AtomicBoolean CLOSE_INTERCEPT = new AtomicBoolean(false);

    private static final Set<SearchInterceptor> interceptors = new HashSet<>(12);

    public DefaultSearchInterceptManager(@Nullable SearchInterceptor... existedInterceptors) {
        if (existedInterceptors != null) CollectionUtils.mergeArrayIntoCollection(existedInterceptors, interceptors);
    }

    @Override
    public void intercept(boolean intercept) {
        CLOSE_INTERCEPT.set(intercept);
    }

    @Override
    public Set<SearchInterceptor> getInterceptor() {
        return interceptors.stream().collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public void doIntercept(SearchContext searchContext) throws Exception {
        if (CLOSE_INTERCEPT.get()) {
            return;
        }
        for (SearchInterceptor interceptor : getInterceptor()) {
            interceptor.intercept(searchContext);
        }
    }

}
