package com.kapp.kappcore.search.core.search;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.core.interceptor.SearchInterceptorRegistry;
import com.kapp.kappcore.search.support.Collector;
import com.kapp.kappcore.search.support.factory.impl.SearchRequestFactory;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * Author:Heping
 * Date: 2024/6/26 15:49
 */
@Slf4j
public abstract class AbstractKappSearchActor implements SearchActor {
    protected final RestHighLevelClient restHighLevelClient;
    protected final SearchInterceptorRegistry searchInterceptorRegistry;
    protected final SearchRequestFactory searchRequestFactory;

    public AbstractKappSearchActor(RestHighLevelClient restHighLevelClient, SearchInterceptorRegistry searchInterceptorRegistry) {
        this.restHighLevelClient = restHighLevelClient;
        this.searchInterceptorRegistry = searchInterceptorRegistry;
        searchRequestFactory = SearchRequestFactory.getInstance();
    }

    protected void intercept(SearchParam searchParam) {
        searchInterceptorRegistry.invoke(searchParam);
    }

    SearchRequest searchTemplate(SearchParam searchParam) {
        this.intercept(searchParam);
        return searchRequestFactory.create(searchParam);
    }


    /**
     * search 入口
     *
     * @param searchRequest   request
     * @param resultCollector result handle
     * @return result
     */
    protected <R> R doSearch(SearchRequest searchRequest,
                             Collector<R> resultCollector) {
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            return resultCollector.collect(response);
        } catch (IOException e) {
            log.error("error when doSearch", e);
            throw new SearchException(ExCode.error);
        }
    }

}
