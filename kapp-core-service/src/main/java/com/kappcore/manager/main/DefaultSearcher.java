package com.kappcore.manager.main;

import com.kappcore.domain.map.SearchResult;
import com.kappcore.manager.context.SearchContext;
import com.kappcore.manager.support.interceptor.register.IInterceptor;
import com.kappcore.manager.request.RequestConstructor;
import com.kappcore.manager.request.index.TagIndexChooser;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DefaultSearcher implements Searcher {



    private final RestHighLevelClient restHighLevelClient;

    private final RequestConstructor<SearchRequest> requestConstructor;

    private final TagIndexChooser tagIndexChooser;

    private final IInterceptor interceptor;

    @Override
    public SearchResult search(SearchContext context) {
        this.before(context);

        SearchRequest request = requestConstructor.get(context);

        IndexRequest index = tagIndexChooser.index(context.tag());

        request.indices(index.index());

        SearchResponse searchResponse;

        try {
            searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("检索服务器异常", e);
        }

        for (SearchHit hit : searchResponse.getHits().getHits()) {
//            hit.
        }

        return null;
    }

    /**
     * 在检索之前，执行所有的拦截器
     * @param context 检索上下文
     */
    @Override
    public void before(SearchContext context) {
        interceptor.intercept(context);
    }

    @Override
    public void after(SearchContext searchContext) {

    }
}
