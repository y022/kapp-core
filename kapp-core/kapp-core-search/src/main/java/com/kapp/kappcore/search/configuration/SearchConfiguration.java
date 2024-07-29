package com.kapp.kappcore.search.configuration;

import com.kapp.kappcore.search.core.interceptor.KappSearchParamInterceptor;
import com.kapp.kappcore.search.core.interceptor.SearchInterceptor;
import com.kapp.kappcore.search.core.interceptor.SearchInterceptorRegistry;
import com.kapp.kappcore.search.core.search.StandardKappSearchActor;
import com.kapp.kappcore.search.endpoint.SearchServiceImpl;
import com.kapp.kappcore.search.endpoint.UpdateServiceImpl;
import lombok.Data;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Author:Heping
 * Date: 2024/6/25 14:10
 */
@Configuration
@SuppressWarnings("all")
public class SearchConfiguration {
    @Bean
    public KappSearchParamInterceptor kappParamInterceptor(){
        return  new KappSearchParamInterceptor();
    }

    @Bean
    public SearchInterceptorRegistry interceptorRegistry(List<SearchInterceptor> interceptors) {
        return new SearchInterceptorRegistry(interceptors);
    }

    @Bean
    public StandardKappSearchActor standardKappSearchActor(RestHighLevelClient restHighLevelClient,
                                                           SearchInterceptorRegistry searchInterceptorRegistry) {
        return new StandardKappSearchActor(restHighLevelClient, searchInterceptorRegistry);
    }

    @Bean
    public SearchServiceImpl searchService(StandardKappSearchActor standardKappSearchActor) {
        return new SearchServiceImpl(standardKappSearchActor);
    }

    @Bean
    public UpdateServiceImpl kappDocUpdateManager(RestHighLevelClient restHighLevelClient) {
        return new UpdateServiceImpl(restHighLevelClient);
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
