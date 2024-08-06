package com.kapp.kappcore.search.endpoint;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.common.ExtSearchRequest;
import com.kapp.kappcore.search.common.SearchResult;
import com.kapp.kappcore.search.core.search.SearchActor;
import com.kapp.kappcore.search.support.Collector;
import com.kapp.kappcore.search.support.factory.impl.ParamFactory;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.model.response.SearchBody;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author:Heping
 * Date: 2024/6/25 17:58
 */
@Slf4j
public class SearcherServiceImpl implements KappDocSearcher {
    private final SearchActor searchActor;

    public SearcherServiceImpl(SearchActor searchActor) {
        this.searchActor = searchActor;
    }

    public SearchResult<?> search(ExtSearchRequest extSearchRequest) throws SearchException {
        SearchParam searchParam = ParamFactory.instance().create(extSearchRequest);
        SearchResult<SearchBody> searchResult = searchActor.search(searchParam, defaultCollector());
        searchResult.pageParam(extSearchRequest.getPageNum(), extSearchRequest.getPageSize());
        return searchResult;
    }

    @Override
    public SearchResult<?> scroll(ExtSearchRequest extSearchRequest) throws SearchException {
        return null;
    }

    Collector<SearchResult<SearchBody>> defaultCollector() {
        return (searchResponse) -> {
            SearchResult<SearchBody> searchResult = new SearchResult<>();
            if (searchResponse == null || searchResponse.isTimedOut()) {
                searchResult.setSuccess(false);
            } else {
                SearchBody searchBody = new SearchBody();
                searchBody.setTook(searchResponse.getTook().getMillis());
                SearchHits hits = searchResponse.getHits();
                searchBody.setTotal(hits.getTotalHits().value);

                List<Map<String, Object>> data = Arrays.stream(hits.getHits()).map(SearchHit::getSourceAsMap).collect(Collectors.toList());
                searchBody.setData(data);
                searchResult.setTook(searchBody.getTook());
                searchResult.setTotal(searchBody.getTotal());
                searchResult.setSuccess(true);
                searchResult.setData(searchBody);
                searchResult.setScrollId(searchResponse.getScrollId());
            }
            return searchResult;
        };
    }
}
