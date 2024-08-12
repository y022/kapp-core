package com.kapp.kappcore.search.core.search;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.core.interceptor.SearchInterceptorRegistry;
import com.kapp.kappcore.search.support.SearchCollector;
import com.kapp.kappcore.search.support.factory.impl.SearchRequestFactory;
import com.kapp.kappcore.search.support.model.constant.Val;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Author:Heping
 * Date: 2024/6/26 15:49
 */
public abstract class AbstractKappSearchActor implements SearchActor {
    protected final RestHighLevelClient restHighLevelClient;
    protected final SearchInterceptorRegistry searchInterceptorRegistry;
    protected final SearchRequestFactory searchRequestFactory;
    private static final Logger log = LoggerFactory.getLogger(StandardKappSearchActor.class.getName());

    public AbstractKappSearchActor(RestHighLevelClient restHighLevelClient, SearchInterceptorRegistry searchInterceptorRegistry) {
        this.restHighLevelClient = restHighLevelClient;
        this.searchInterceptorRegistry = searchInterceptorRegistry;
        searchRequestFactory = SearchRequestFactory.getInstance();
    }

    protected void intercept(SearchParam searchParam) {
        searchInterceptorRegistry.invoke(searchParam);
    }

    protected SearchRequest searchTemplate(SearchParam searchParam) {
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
                             SearchCollector<SearchResponse, R> resultCollector) {
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            return resultCollector.collect(response);
        } catch (IOException e) {
            log.error("error when doSearch", e);
            throw new SearchException(ExCode.error);
        }
    }

    protected <R> R scroll(SearchScrollRequest searchScrollRequest, SearchCollector<SearchResponse, R> collector) {
        searchScrollRequest.scroll(Val.SCROLL_KEEP_ALIVE);
        try {
            SearchResponse scroll = restHighLevelClient.scroll(searchScrollRequest, RequestOptions.DEFAULT);
            R r = collector.collect(scroll);
            if (null != scroll.getHits() && scroll.getHits().getHits().length > 100) {
                ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
                clearScrollRequest.addScrollId(searchScrollRequest.scrollId());
                restHighLevelClient.clearScrollAsync(clearScrollRequest, RequestOptions.DEFAULT, new ActionListener<>() {
                    @Override
                    public void onResponse(ClearScrollResponse clearScrollResponse) {
                        log.info("clear scroll content success! scrollId:{}", searchScrollRequest.scrollId());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        log.info("clear scroll content failed! scrollId:{}", searchScrollRequest.scrollId());
                    }
                });
            }
            return r;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
