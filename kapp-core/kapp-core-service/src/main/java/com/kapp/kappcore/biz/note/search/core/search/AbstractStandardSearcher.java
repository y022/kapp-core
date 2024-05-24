package com.kapp.kappcore.biz.note.search.core.search;

import com.kapp.kappcore.biz.note.search.context.SearchContext;
import com.kapp.kappcore.biz.note.search.support.interceptor.register.SearchInterceptManager;
import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

@Slf4j
public abstract class AbstractStandardSearcher implements StandardSearcher {
    private final SearchInterceptManager interceptManager;
    private final RestHighLevelClient restHighLevelClient;


    public SearchResponse doSearch(SearchRequest request, SearchContext context) {
        this.before(context);
        SearchResponse response;
        try {
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("search server exception: ", e);
            throw new SearchException(ExCode.search_server_error, "检索异常!");
        }
        this.after(response);
        return response;
    }


    public AbstractStandardSearcher(SearchInterceptManager interceptManager, RestHighLevelClient restHighLevelClient) {
        this.interceptManager = interceptManager;
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public void before(SearchContext context) {
        try {
            interceptManager.doIntercept(context);
        } catch (Exception e) {

        }
    }

    @Override
    public void after(SearchResponse response) {
        //nothing to do now
    }


}
