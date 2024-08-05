package com.kapp.kappcore.search.support.factory.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapp.kappcore.model.constant.SearchVal;
import com.kapp.kappcore.search.support.factory.SearchFactory;
import com.kapp.kappcore.search.support.model.param.SearchMetrics;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author:Heping
 * Date: 2024/6/26 16:19
 */
public abstract class AbstractSearchRequestFactory<S, T extends SearchMetrics, R> implements SearchFactory<T, R> {

    /**
     * set up search index
     *
     * @param index doc-index
     * @return request
     */
    protected SearchRequest init(String index) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        return searchRequest;
    }

    protected SearchSourceBuilder searchSourceBuilder() {
        return Query.searchSourceBuilder();
    }

    protected BoolQueryBuilder boolQueryBuilder() {
        return Query.boolQuery();
    }


    protected QueryBuilder searchAll() {
        return Query.all();
    }

    protected void appendQuery(String searchVal, Object searchField, SearchSourceBuilder sourceBuilder) {
        if (sourceBuilder == null || !StringUtils.hasText(searchVal) || Objects.isNull(searchField)) {
            return;
        }
        sourceBuilder.query(Query.matchQuery(searchVal, searchField));
    }

    protected void multiQuery(String searchVal, String searchField, String boolCondition, BoolQueryBuilder boolQueryBuilder) {
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

    protected void highlight(String preTag, String postTag, SearchSourceBuilder sourceBuilder, String... fields) {
        if (fields != null && fields.length > 0) {
            sourceBuilder.highlighter(Highlight.highlight(Arrays.stream(fields).collect(Collectors.toSet()), preTag, postTag));
        }
    }

    protected IndexRequest add(String index, String id, Map<String, Object> body) {
        return Update.add(index, id, body);
    }

    protected UpdateRequest update(String index, String id, Map<String, Object> body) {
        return Update.update(index, id, body);
    }

    protected DeleteRequest delete(String index, String id) {
        return Update.delete(index, id);
    }

    protected BulkRequest addBulk(String index, List<Map<String, Object>> content) {
        return Update.bulk(index, content, new Update.ReqLam() {
            @Override
            public DocWriteRequest<?> get(String indexName, String indexId, Map<String, Object> content) {
                return add(indexName, indexId, content);
            }
        });
    }

    protected BulkRequest updateBulk(String index, List<Map<String, Object>> content) {
        return Update.bulk(index, content, new Update.ReqLam() {
            @Override
            public DocWriteRequest<?> get(String indexName, String indexId, Map<String, Object> content) {
                return update(indexName, indexId, content);
            }
        });
    }

    protected BulkRequest deleteBulk(String index, Set<String> ids) {
        return Update.bulk(index, ids, new Update.ReqLam() {
            @Override
            public DocWriteRequest<?> get(String indexName, String indexId, Map<String, Object> content) {
                return delete(indexName, indexId);
            }
        });
    }

    protected List<TermsAggregationBuilder> aggByBuckets(Set<String> aggKeys, Map<String, Set<String>> subAggKeys) {
        return Agg.buckets(aggKeys, subAggKeys);
    }

    protected TermsAggregationBuilder aggByBucket(String aggKey) {
        return Agg.bucket(aggKey);
    }

    protected Map<String, Object> convertMap(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(o, new TypeReference<Map<String, Object>>() {
        });
    }

    protected static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    protected static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }


    protected static List<SortBuilder<?>> getSort(Set<String> sortKeys) {
        return Sort.sort(sortKeys);
    }

    protected static class Query {

        protected static SearchSourceBuilder searchSourceBuilder() {
            return new SearchSourceBuilder();
        }

        protected static BoolQueryBuilder boolQuery() {
            return new BoolQueryBuilder();
        }

        protected static QueryBuilder all() {
            return QueryBuilders.matchAllQuery();
        }

        protected static MatchQueryBuilder matchQuery(String searchVal, Object searchField) {
            return QueryBuilders.matchQuery(searchVal, searchField);
        }

        protected static TermQueryBuilder term(String key, Object value) {
            return QueryBuilders.termQuery(key, value);
        }

        protected static TermsQueryBuilder terms(String key, Object... values) {
            return QueryBuilders.termsQuery(key, values);
        }

        protected static MatchQueryBuilder match(String key, Object value) {
            return QueryBuilders.matchQuery(key, value);
        }

        protected static MatchPhraseQueryBuilder phrase(String key, Object value) {
            return QueryBuilders.matchPhraseQuery(key, value);
        }

        protected static MatchQueryBuilder match(String key, Object value, String analyzer) {
            return QueryBuilders.matchQuery(key, value).analyzer(analyzer);
        }

        protected static FuzzyQueryBuilder fuzzy(String key, Object value) {
            return QueryBuilders.fuzzyQuery(key, value).fuzziness(Fuzziness.AUTO);
        }

        protected static RangeQueryBuilder range(String key, Object lte, Object gte, boolean includeLow, boolean includeHigh) {
            return QueryBuilders.rangeQuery(key).gte(gte).lte(lte).includeLower(includeHigh).includeUpper(includeLow);
        }


    }

    /**
     * agg class
     */
    protected static class Agg {
        private static List<TermsAggregationBuilder> buckets(Set<String> aggKeys, Map<String, Set<String>> subAggKeyMap) {
            if (isEmpty(aggKeys)) {
                return Collections.emptyList();
            }
            boolean ifSubBucket = !isEmpty(subAggKeyMap);
            return aggKeys.stream().map(key -> {
                TermsAggregationBuilder tb = bucket(key);
                return subBucket(tb, ifSubBucket ? subAggKeyMap.get(key) : null);
            }).collect(Collectors.toList());
        }

        /**
         * Do not set the order of the bucket
         *
         * @param aggKey key for bucket
         * @return tb
         */
        private static TermsAggregationBuilder bucket(String aggKey) {
            return AggregationBuilders.terms(aggKey).field(aggKey + ".keyword");
        }

        /**
         * set up sub bucket
         *
         * @param mainTb     mainTb
         * @param subAggKeys subTb's key
         * @return mainTb
         */
        private static TermsAggregationBuilder subBucket(TermsAggregationBuilder mainTb, Set<String> subAggKeys) {
            if (isEmpty(subAggKeys)) {
                return mainTb;
            }
            for (String subAggKey : subAggKeys) {
                TermsAggregationBuilder subTb = bucket(subAggKey);
                mainTb.subAggregation(subTb);
            }
            return mainTb;
        }
    }

    protected static class Update {
        private static final Logger log = LoggerFactory.getLogger(Update.class);

        protected interface ReqLam {
            DocWriteRequest<?> get(String indexName, String indexId, Map<String, Object> content);
        }

        protected static UpdateRequest updateRequest() {
            return new UpdateRequest();
        }

        protected static IndexRequest indexRequest() {
            return new IndexRequest();
        }

        protected static IndexRequest indexRequest(String indexName) {
            return new IndexRequest(indexName);
        }

        protected static DeleteRequest deleteRequest() {
            return new DeleteRequest();
        }

        protected static DeleteRequest deleteRequest(String indexName) {
            return new DeleteRequest(indexName);
        }

        protected static BulkRequest bulkRequest() {
            return new BulkRequest();
        }

        protected static BulkRequest bulkRequest(String indexName) {
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

    protected static class Sort {
        private static List<SortBuilder<?>> sort(Set<String> sortKeys) {
            return !isEmpty(sortKeys) ? sortKeys.stream().map(SortBuilders::fieldSort).collect(Collectors.toList()) : List.of();
        }

        public static SortBuilder<?> sortBuilder(String sortKey, String order) {
            return SortBuilders.fieldSort(sortKey).order(SortOrder.fromString(order));
        }
    }
}
