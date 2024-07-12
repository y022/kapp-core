package com.kapp.kappcore.search.support.builder.impl;

import com.kapp.kappcore.model.constant.SearchVal;
import com.kapp.kappcore.search.support.builder.SearchFactory;
import com.kapp.kappcore.search.support.model.condition.UpdateCondition;
import com.kapp.kappcore.search.support.model.condition.ValCondition;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.option.DocOption;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Author:Heping
 * Date: 2024/6/30 18:41
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRequestFactory implements SearchFactory<SearchParam, ActionRequest> {
    private static final UpdateRequestFactory INSTANCE = new UpdateRequestFactory();

    public static UpdateRequestFactory getInstance() {
        return INSTANCE;
    }


    @Override
    public ActionRequest create(SearchParam searchParam) {
        ValCondition condition = searchParam.getCondition();
        UpdateCondition upCondition = (UpdateCondition) condition;
        UpdateReqBuilder instance = UpdateReqBuilder.INSTANCE;

        DocOption option = condition.option();
        switch (option) {
            case ADD:
            case UPDATE:
                return instance.bulk_upsert(upCondition.getActualIndex(), upCondition.getUpdateMap());

        }


        return null;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class UpdateReqBuilder {
        public static final Logger log = LoggerFactory.getLogger(AbstractSearchRequestFactory.Update.class);
        public static final UpdateReqBuilder INSTANCE = new UpdateReqBuilder();

        protected interface DocWriter {
            DocWriteRequest<?> source(String indexName, String indexId, Map<String, Object> content);
        }

        protected BulkRequest bulk_upsert(String index, List<Map<String, Object>> content) {
            return bulk(index, content, this::upsert);
        }

        private BulkRequest bulk(String indexName, List<Map<String, Object>> content, DocWriter docWriter) {
            BulkRequest bulkRequest = new BulkRequest(indexName);
            for (Map<String, Object> term : content) {
                bulkRequest.add(docWriter.source(indexName, Optional.ofNullable((String) term.get(SearchVal.ID)).orElseThrow(() -> new IllegalArgumentException("doc id miss!")), term));
            }
            return bulkRequest;
        }

        private UpdateRequest upsert(String indexName, String indexId, Map<String, Object> content) {
            return new UpdateRequest(indexName, indexId)
                    .upsert(content, XContentType.JSON);
        }


    }

}
