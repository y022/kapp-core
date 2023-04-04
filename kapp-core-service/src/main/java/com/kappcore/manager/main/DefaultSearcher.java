package com.kappcore.manager.main;

import com.kappcore.domain.map.SearchResult;
import com.kappcore.manager.context.SearchContext;
import com.kappcore.manager.intercept.SearchInterceptor;
import com.kappcore.manager.model.SearchTip;
import com.kappcore.manager.request.RequestConstructor;
import com.kappcore.manager.request.index.IndexChoose;
import com.kappcore.manager.request.index.TagIndexChooser;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultSearcher implements Searcher, BeanFactoryAware {

    private List<SearchInterceptor> interceptors;

    private final RestHighLevelClient restHighLevelClient;

    private final RequestConstructor<SearchRequest> requestConstructor;
    private TagIndexChooser tagIndexChooser;

    /**
     * 获取所有的拦截器
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        beanFactory.getBeanProvider(SearchInterceptor.class).forEach(interceptors::add);
    }

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

    @Override
    public void before(SearchContext context) {
        for (SearchInterceptor interceptor : interceptors) {
            SearchTip tip = interceptor.intercept(context);
            if (tip.exception()) {
                throw new IllegalArgumentException(tip.getTip());
            }
        }
    }

    @Override
    public void after(SearchContext searchContext) {

    }
}
