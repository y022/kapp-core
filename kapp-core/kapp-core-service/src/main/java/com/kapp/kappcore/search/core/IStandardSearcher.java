package com.kapp.kappcore.search.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapp.kappcore.constant.ExCode;
import com.kapp.kappcore.exception.SearchException;
import com.kapp.kappcore.search.request.RequestConstructor;
import com.kapp.kappcore.search.support.interceptor.register.IInterceptor;
import com.kapp.kappcore.search.context.SearchContext;
import com.kapp.kappcore.search.request.index.TagIndexChooser;
import com.kapp.kappcore.search.support.result.SearchResult;
import com.kapp.kappcore.search.support.result.SearchResultCollector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class IStandardSearcher implements StandardSearcher {

    private final RestHighLevelClient restHighLevelClient;

    private final RequestConstructor<SearchRequest> requestConstructor;

    private final TagIndexChooser tagIndexChooser;

    private final IInterceptor interceptor;


    @Override
    public SearchResult search(SearchContext context) {
        this.before(context);

        SearchRequest request = requestConstructor.get(context);

        request.indices(tagIndexChooser.index(context.tag()).indices());

        SearchResponse searchResponse;

        try {
            searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("search server exception!", e);
            throw new SearchException(ExCode.search_server_error, "检索服务异常!");
        }
        return SearchResultCollector.doCollect(searchResponse,context);
    }

    @Override
    public void before(SearchContext context) {
        interceptor.intercept(context);
    }

    @Override
    public void after(SearchResponse searchContext) {

    }
}
