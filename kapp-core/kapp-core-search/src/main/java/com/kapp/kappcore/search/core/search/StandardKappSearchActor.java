package com.kapp.kappcore.search.core.search;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.core.interceptor.SearchInterceptorRegistry;
import com.kapp.kappcore.search.support.Collector;
import com.kapp.kappcore.search.support.factory.impl.SearchRequestFactory;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * Author:Heping
 * Date: 2024/6/26 15:52
 */
public class StandardKappSearchActor extends AbstractKappSearchActor {
    private final SearchRequestFactory searchRequestFactory;
    public StandardKappSearchActor(RestHighLevelClient restHighLevelClient, SearchInterceptorRegistry searchInterceptorRegistry) {
        super(restHighLevelClient, searchInterceptorRegistry);
        this.searchRequestFactory = SearchRequestFactory.getInstance();
    }

    @Override
    public <R> R search(SearchParam searchParam, Collector<R> resultCollector) throws SearchException {
        super.intercept(searchParam);
        SearchRequest searchRequest = searchRequestFactory.create(searchParam);
        return normalSearch(searchRequest, resultCollector);
    }


}
