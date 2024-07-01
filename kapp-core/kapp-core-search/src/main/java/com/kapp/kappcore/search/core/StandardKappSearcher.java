package com.kapp.kappcore.search.core;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.support.builder.impl.SearchRequestFactory;
import com.kapp.kappcore.search.core.interceptor.InterceptorRegistry;
import com.kapp.kappcore.search.support.Collector;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

/**
 * Author:Heping
 * Date: 2024/6/26 15:52
 */
@Slf4j
@Service
public class StandardKappSearcher extends AbstractKappSearcher {
    private final InterceptorRegistry interceptorRegistry;

    public StandardKappSearcher(RestHighLevelClient restHighLevelClient, InterceptorRegistry interceptorRegistry) {
        super(restHighLevelClient);
        this.interceptorRegistry = interceptorRegistry;
    }

    @Override
    public <R> R search(SearchParam searchParam, Collector<R> resultCollector) throws SearchException {
        interceptorRegistry.invoke(searchParam);
        SearchRequest searchRequest = SearchRequestFactory.getInstance().create(searchParam);
        return doSearch(searchRequest, resultCollector);
    }


}
