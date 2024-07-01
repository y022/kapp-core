package com.kapp.kappcore.search.configuration;

import com.kapp.kappcore.search.core.interceptor.InterceptorRegistry;
import com.kapp.kappcore.search.core.interceptor.SearchInterceptor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Author:Heping
 * Date: 2024/6/25 14:10
 */
@Configuration
public class SearchConfiguration {

    @Bean
    public InterceptorRegistry interceptorRegistry(@Autowired(required = false) List<SearchInterceptor> interceptors) {
        return new InterceptorRegistry(interceptors);
    }

    @Data
    @Configuration
    @ConditionalOnProperty("com.kapp.search")
    public static class SearchField {
        /**
         * 一次分组最多返回的桶数量最大限制
         */
        private int maxGroupBucketCount = 100;
        /**
         * 单桶内的文档数量最大限制
         */
        private int maxGroupSubBucketCount = 8;


    }
}
