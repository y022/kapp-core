package com.kapp.kappcore.service.biz.note.search.support.result;

import com.kapp.kappcore.model.biz.Sch;
import com.kapp.kappcore.model.biz.domain.search.SearchResult;
import com.kapp.kappcore.model.biz.domain.search.SearchBody;
import com.kapp.kappcore.service.biz.note.search.context.SearchContext;
import com.kapp.kappcore.service.biz.note.search.index.TagIndex;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.util.*;
import java.util.stream.Collectors;

public class SearchResponseCollector {

    @SuppressWarnings("all")
    public static SearchResult<Sch> doCollectSearch(SearchResponse response, SearchContext context) {
        SearchHits hits = response.getHits();
        List<SearchBody> body = Arrays.stream(hits.getHits()).parallel().map(hitItem -> {
            SearchBody bodyItem = new SearchBody();
            bodyItem.setDocId(hitItem.getId());
            TagIndex.getTag(hitItem.getIndex());
            bodyItem.setBody(hitItem.getSourceAsString());
            HashMap<String, Object> highlightMap = new HashMap<>();
            if (context.requireHighlight()) {
                Set<String> requireHighlightFields = context.highlightFields();
                Map<String, HighlightField> highlightFields = hitItem.getHighlightFields();
                for (String requireHighlightField : requireHighlightFields) {
                    HighlightField highlightField = highlightFields.get(requireHighlightField);
                    if (highlightField != null) {
                        StringBuilder sb = new StringBuilder();
                        for (Text fragment : highlightField.fragments()) {
                            sb.append(fragment.toString());
                        }
                        highlightMap.put(requireHighlightField, sb.toString());
                    }
                }
            }
            bodyItem.setHighlightContent(highlightMap);
            return bodyItem;
        }).collect(Collectors.toList());
        SearchResult<SearchBody> result = new SearchResult<>(response.getTook().millis(), hits.getTotalHits().value);
        result.warp(body);
        return (SearchResult) result;
    }


    public static SearchResult<?> doCollectGroup(SearchResponse response, SearchContext context) {
//        Aggregations aggregations = response.getAggregations();
//        SearchGroup searchGroup = new SearchGroup();
//
//        for (Aggregation aggregation : aggregations) {
//            List<? extends Terms.Bucket> buckets = ((ParsedStringTerms) aggregation).getBuckets();
//            searchGroup.setCount(buckets.size());
//            List<GroupBody> group = buckets.stream().map(bucket -> {
//                GroupBody groupBody = new GroupBody();
//                groupBody.setDocCount(bucket.getDocCount());
//                groupBody.setGroupField(bucket.getKeyAsString());
//                return groupBody;
//            }).collect(Collectors.toList());
//            searchGroup.setContent(group);
//        }
//        SearchResult<SearchGroup> searchResult = new SearchResult<>();
        return null;
    }


}
