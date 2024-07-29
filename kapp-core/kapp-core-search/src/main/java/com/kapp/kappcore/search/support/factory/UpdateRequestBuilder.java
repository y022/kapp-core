package com.kapp.kappcore.search.support.factory;

import com.kapp.kappcore.model.constant.SearchVal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRequestBuilder {
    private static final UpdateRequestBuilder INSTANCE = new UpdateRequestBuilder();

    public static UpdateRequestBuilder getInstance() {
        return INSTANCE;
    }

    protected interface DocWriter {
        DocWriteRequest<?> source(String indexName, String indexId, Map<String, Object> content);
    }

    public BulkRequest upsert(String index, List<Map<String, Object>> content) {
        return bulk(index, content, this::warpUpdate);
    }

    public BulkRequest del(String indexName, Set<String> indexIds) {
        return bulk(indexName, indexIds, (name, id, content) -> new DeleteRequest(name, id));
    }

    public DeleteByQueryRequest delByQuery(String indexName, QueryBuilder qb) {
        DeleteByQueryRequest delByQuery = new DeleteByQueryRequest(indexName);
        delByQuery.setQuery(qb);
        return delByQuery;
    }


    private BulkRequest bulk(String indexName, List<Map<String, Object>> content, DocWriter docWriter) {
        BulkRequest bulkRequest = new BulkRequest(indexName);
        for (Map<String, Object> term : content) {
            bulkRequest.add(docWriter.source(indexName, Optional.ofNullable((String) term.get(SearchVal.ID)).orElseThrow(() -> new IllegalArgumentException("doc id miss!")), term));
        }
        return bulkRequest;
    }

    private BulkRequest bulk(String indexName, Set<String> indexIds, DocWriter docWriter) {
        BulkRequest bulkRequest = new BulkRequest(indexName);
        if (indexIds != null && !indexIds.isEmpty()) {
            for (String indexId : indexIds) {
                bulkRequest.add(docWriter.source(indexName, indexId, null));
            }
        }
        return bulkRequest;
    }

    private UpdateRequest warpUpdate(String indexName, String indexId, Map<String, Object> content) {
            return new UpdateRequest(indexName, indexId)
                    .doc(content) // 设置用于更新的文档内容
                    .upsert(content, XContentType.JSON); // 在文档不存在时插入新的文档
        }

}