package com.kapp.kappcore.service.biz.note.search.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapp.kappcore.model.constant.SearchVal;
import com.kapp.kappcore.service.biz.note.search.request.index.TagIndexChooser;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractRequestConstructor {

    public SearchRequest init(String tag) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(TagIndexChooser.indexName(tag));
        return searchRequest;
    }

    public SearchSourceBuilder searchSourceBuilder() {
        return Query.searchSourceBuilder();
    }

    public BoolQueryBuilder boolQueryBuilder() {
        return Query.boolQueryBuilder();
    }

    public SearchSourceBuilder searchAll() {
        return Query.all(null);
    }

    public void appendQuery(String searchVal, String searchField, SearchSourceBuilder sourceBuilder) {
        if (sourceBuilder == null || !StringUtils.hasText(searchVal) || !StringUtils.hasText(searchField)) {
            return;
        }
        sourceBuilder.query(Query.matchQuery(searchVal, searchField));
    }

    public void MultiQuery(String searchVal, String searchField, String boolCondition, BoolQueryBuilder boolQueryBuilder) {
        if (boolQueryBuilder == null || !StringUtils.hasText(searchVal) || !StringUtils.hasText(searchField)) {
            return;
        }
        MatchQueryBuilder query = Query.matchQuery(searchVal, searchField);
        switch (boolCondition) {
            case SearchVal.MUST:
                boolQueryBuilder.must(query);
                break;
            case SearchVal.MUST_NOT:
                boolQueryBuilder.mustNot(query);
                break;
            case SearchVal.SHOULD:
                boolQueryBuilder.should(query);
                break;
            default:
                throw new IllegalArgumentException("boolCondition not supported: " + boolCondition);
        }
    }

    public TermsAggregationBuilder agg(String groupField) {
        return AggregationBuilders.terms(groupField).field(groupField + ".keyword");
    }

    public TermsAggregationBuilder agg(Set<String> groupFields) {
        List<String> fields = new ArrayList<>(groupFields);
        String mainAggField = fields.get(0);
        TermsAggregationBuilder mainTermAgg = AggregationBuilders.terms(mainAggField).field(mainAggField + ".keyword");
        fields.remove(mainAggField);
        for (String field : fields) {
            TermsAggregationBuilder subTermAgg = AggregationBuilders.terms(field).field(field + ".keyword");
            mainTermAgg.subAggregation(subTermAgg);

        }
        return mainTermAgg;
    }

    public void highlight(String preTag, String postTag, SearchSourceBuilder sourceBuilder, String... fields) {
        if (fields != null && fields.length > 0) {
            sourceBuilder.highlighter(Highlight.highlight(Arrays.stream(fields).collect(Collectors.toSet()), preTag, postTag));
        }
    }

    public IndexRequest add(String index, String id, Map<String, Object> body) {
        return Update.add(index, id, body);
    }

    public UpdateRequest update(String index, String id, Map<String, Object> body) {
        return Update.update(index, id, body);
    }

    public DeleteRequest delete(String index, String id) {
        return Update.delete(index, id);
    }

    public BulkRequest addBulk(String index, List<Map<String, Object>> content) {
        return Update.bulk(index, content, new Update.ReqLam() {
            @Override
            public DocWriteRequest<?> get(String indexName, String indexId, Map<String, Object> content) {
                return add(indexName, indexId, content);
            }
        });
    }

    public BulkRequest updateBulk(String index, List<Map<String, Object>> content) {
        return Update.bulk(index, content, new Update.ReqLam() {
            @Override
            public DocWriteRequest<?> get(String indexName, String indexId, Map<String, Object> content) {
                return update(indexName, indexId, content);
            }
        });
    }

    public BulkRequest deleteBulk(String index, Set<String> ids) {
        return Update.bulk(index, ids, new Update.ReqLam() {
            @Override
            public DocWriteRequest<?> get(String indexName, String indexId, Map<String, Object> content) {
                return delete(indexName, indexId);
            }
        });
    }

    public Map<String, Object> convertMap(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(o, new TypeReference<Map<String, Object>>() {
        });
    }

    protected static class Query {

        private static SearchSourceBuilder searchSourceBuilder() {
            return new SearchSourceBuilder();
        }

        private static BoolQueryBuilder boolQueryBuilder() {
            return new BoolQueryBuilder();
        }

        private static SearchSourceBuilder all(@Nullable SearchSourceBuilder sourceBuilder) {
            if (sourceBuilder == null) {
                sourceBuilder = searchSourceBuilder();
            }
            MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
            sourceBuilder.query(matchAllQueryBuilder);
            return sourceBuilder;
        }

        private static MatchQueryBuilder matchQuery(String searchVal, String searchField) {
            return QueryBuilders.matchQuery(searchField, searchVal);
        }

    }

    protected static class Update {
        private static final Logger log = LoggerFactory.getLogger(Update.class);

        private interface ReqLam {
            DocWriteRequest<?> get(String indexName, String indexId, Map<String, Object> content);
        }

        private static UpdateRequest updateRequest() {
            return new UpdateRequest();
        }

        private static IndexRequest indexRequest() {
            return new IndexRequest();
        }

        private static IndexRequest indexRequest(String indexName) {
            return new IndexRequest(indexName);
        }

        private static DeleteRequest deleteRequest() {
            return new DeleteRequest();
        }

        private static DeleteRequest deleteRequest(String indexName) {
            return new DeleteRequest(indexName);
        }

        private static BulkRequest bulkRequest() {
            return new BulkRequest();
        }

        private static BulkRequest bulkRequest(String indexName) {
            return new BulkRequest(indexName);
        }

        private static void indexCheck(String indexName) {
            if (indexName == null) {
                throw new IllegalArgumentException("indexName miss!");
            }
        }

        private static IndexRequest add(String indexName, String indexId, Map<String, Object> content) {
            indexCheck(indexName);
            IndexRequest add = indexRequest(indexName);
            add.index(indexName);
            add.id(indexId);
            add.source(content);
            return add;
        }

        private static UpdateRequest update(String indexName, String indexId, Map<String, Object> content) {
            indexCheck(indexName);
            UpdateRequest update = updateRequest();
            update.index(indexName);
            update.id(indexId);
            update.doc(content);
            return update;
        }

        private static DeleteRequest delete(String indexName, String indexId) {
            indexCheck(indexName);
            DeleteRequest delete = deleteRequest(indexName);
            delete.index(indexName);
            delete.id(indexId);
            return delete;
        }

        private static BulkRequest bulk(String indexName, List<Map<String, Object>> content, ReqLam reqLam) {
            BulkRequest bulkRequest = bulkRequest(indexName);
            for (Map<String, Object> term : content) {
                bulkRequest.add(reqLam.get(indexName, Optional.ofNullable((String) term.get(SearchVal.ID)).orElseThrow(() -> new IllegalArgumentException("indexId miss!")), term));
            }
            return bulkRequest;
        }

        private static BulkRequest bulk(String indexName, Set<String> ids, ReqLam reqLam) {
            BulkRequest bulkRequest = bulkRequest(indexName);
            for (String indexId : ids) {
                bulkRequest.add(reqLam.get(indexName, indexId, null));
            }
            return bulkRequest;
        }

    }

    protected static class Highlight {
        private static HighlightBuilder highlight(Set<String> fields, String preTag, String postTag) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            for (String field : fields) {
                HighlightBuilder.Field highlightField = new HighlightBuilder.Field(field);
                highlightField.highlighterType("unified");
                highlightBuilder.field(highlightField);
            }
            highlightBuilder.preTags(preTag);
            highlightBuilder.postTags(postTag);
            return highlightBuilder;
        }
    }
}
