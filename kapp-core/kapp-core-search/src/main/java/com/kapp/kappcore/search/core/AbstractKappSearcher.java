package com.kapp.kappcore.search.core;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.support.Collector;
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
public abstract class AbstractKappSearcher implements Searcher {
    private final RestHighLevelClient restHighLevelClient;
    public AbstractKappSearcher(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
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
