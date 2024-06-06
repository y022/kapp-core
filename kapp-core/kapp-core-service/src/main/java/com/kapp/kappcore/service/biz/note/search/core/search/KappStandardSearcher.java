package com.kapp.kappcore.service.biz.note.search.core.search;

import com.kapp.kappcore.model.biz.domain.search.SearchResult;
import com.kapp.kappcore.service.biz.note.search.context.SearchContext;
import com.kapp.kappcore.service.biz.note.search.request.RequestConstructor;
import com.kapp.kappcore.service.biz.note.search.support.interceptor.register.SearchInterceptManager;
import com.kapp.kappcore.service.biz.note.search.support.result.SearchResponseCollector;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KappStandardSearcher extends AbstractStandardSearcher {
    public KappStandardSearcher(SearchInterceptManager interceptManager, RestHighLevelClient restHighLevelClient) {
        super(interceptManager, restHighLevelClient);
    }

    @Override
    public SearchResult<?> search(SearchContext context) {
        SearchRequest request = RequestConstructor.CONSTRUCTOR.normalSearch(context);
        SearchResponse searchResponse = doSearch(request, context);
        return SearchResponseCollector.doCollectSearch(searchResponse, context);
    }

    @Override
    public SearchResult<?> group(SearchContext context) {
        SearchRequest request = RequestConstructor.CONSTRUCTOR.group(context);
        SearchResponse searchResponse = doSearch(request, context);
        return SearchResponseCollector.doCollectGroup(searchResponse, context);
    }


}
