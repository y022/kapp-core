package com.kapp.kappcore.search.support.result;

import com.kapp.kappcore.search.context.SearchContext;
import com.kapp.kappcore.search.index.TagIndex;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHits;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchResultCollector {


    public static SearchResult doCollect(SearchResponse response, SearchContext context) {

        ComplexSearchResult result = new ComplexSearchResult();
        result.setTook(response.getTook().getMillis());

        SearchHits hits = response.getHits();
        result.setTotal(hits.getTotalHits().value);
        result.setSearchPage(context.searchPage());
        result.setSearchPageSize(context.searchSize());

        List<ISearchBody> bodyList = Arrays.stream(hits.getHits()).parallel().map(hitItem -> {
            ISearchBody body = new ISearchBody();
            body.setDocId(hitItem.getId());
            TagIndex.getTag(hitItem.getIndex());
            body.setBody(hitItem.getSourceAsString());
            return body;
        }).collect(Collectors.toList());

        result.setBody(bodyList);

        return result;
    }
}
