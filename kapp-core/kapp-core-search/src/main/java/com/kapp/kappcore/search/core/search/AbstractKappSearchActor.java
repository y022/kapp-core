package com.kapp.kappcore.search.core.search;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.core.interceptor.SearchInterceptorRegistry;
import com.kapp.kappcore.search.support.SearchCallBack;
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
    private static final Logger log = LoggerFactory.getLogger(AbstractKappSearchActor.class);

    public AbstractKappSearchActor(RestHighLevelClient restHighLevelClient, SearchInterceptorRegistry searchInterceptorRegistry) {
        this.restHighLevelClient = restHighLevelClient;
        this.searchInterceptorRegistry = searchInterceptorRegistry;
        searchRequestFactory = SearchRequestFactory.getInstance();
    }

    protected void intercept(SearchParam searchParam) {
        searchInterceptorRegistry.invoke(searchParam);
    }

    protected SearchRequest createSearchRequest(SearchParam searchParam) {
        this.intercept(searchParam);
        return searchRequestFactory.create(searchParam);
    }

    protected void printException(Exception e) {
        log.error("occur error when Search", e);
        throw new SearchException(ExCode.error);
    }

    /**
     * search 入口
     *
     * @param searchRequest request
     * @param callback      result handle
     * @return result
     */
    protected <R> R doSearch(SearchRequest searchRequest,
                             SearchCallBack<SearchResponse, R> callback) {
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            return callback.process(response);
        } catch (IOException e) {
            printException(e);
        }
        return null;
    }

    /**
     * scroll search
     *
     * @param searchScrollRequest request
     * @param callBack            search result handle
     * @param <R>                 result-type{{{@link com.kapp.kappcore.search.common.SearchResult<com.kapp.kappcore.search.support.model.response.SearchBody>}}}
     * @return result
     */
    protected <R> R scroll(SearchScrollRequest searchScrollRequest, SearchCallBack<SearchResponse, R> callBack) {
        searchScrollRequest.scroll(Val.SCROLL_KEEP_ALIVE);
        try {
            SearchResponse scroll = restHighLevelClient.scroll(searchScrollRequest, RequestOptions.DEFAULT);
            R r = callBack.process(scroll);
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
            printException(e);
        }
        return null;
    }
}
