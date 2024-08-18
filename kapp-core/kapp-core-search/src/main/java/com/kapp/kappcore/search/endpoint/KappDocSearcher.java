package com.kapp.kappcore.search.endpoint;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.common.ExtSearchRequest;
import com.kapp.kappcore.search.common.SearchResult;
import com.kapp.kappcore.search.support.SearchCallBack;
import com.kapp.kappcore.search.support.model.response.SearchBody;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author:Heping
 * Date: 2024/7/26 17:33
 */
public interface KappDocSearcher {
    SearchResult<?> search(ExtSearchRequest extSearchRequest) throws SearchException;


    default SearchCallBack<SearchResponse, SearchResult<SearchBody>> defaultSearchCollector() {
        return (response) -> {
            SearchResult<SearchBody> searchResult = new SearchResult<>();
            if (response == null || response.isTimedOut()) {
                searchResult.setSuccess(false);
            } else {
                SearchBody searchBody = new SearchBody();
                searchBody.setTook(response.getTook().getMillis());
                SearchHits hits = response.getHits();
                if (hits.getTotalHits() != null) {
                    searchBody.setTotal(hits.getTotalHits().value);
                }
                List<Map<String, Object>> data = Arrays.stream(hits.getHits()).map(SearchHit::getSourceAsMap).collect(Collectors.toList());
                searchBody.setData(data);
                searchResult.setTook(searchBody.getTook());
                searchResult.setTotal(searchBody.getTotal());
                searchResult.setSuccess(true);
                searchResult.setData(searchBody);
                searchResult.setScrollId(response.getScrollId());
            }
            return searchResult;
        };
    }
}
