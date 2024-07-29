package com.kapp.kappcore.search.endpoint;

import com.kapp.kappcore.search.common.SearchResult;
import com.kapp.kappcore.search.support.model.response.UpdateBody;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.bulk.BulkResponse;

import java.util.List;
import java.util.Map;

/**
 * Author:Heping
 * Date: 2024/7/26 17:05
 */
public interface KappDockUpdate {
    SearchResult<UpdateBody> update(Map<String, Object> data, String indexName);

    SearchResult<UpdateBody> update_bulk(List<Map<String, Object>> data, String indexName);

    void update_async(Map<String, Object> data, String indexName);

    void update_async(List<Map<String, Object>> data, String indexName);

    default SearchResult<UpdateBody> collectUpdate(BulkResponse response) {
        SearchResult<UpdateBody> searchResult = new SearchResult<>();
        UpdateBody updateBody = new UpdateBody();
        response.iterator().forEachRemaining(item -> updateBody.wire(item.getId(), !item.isFailed()));
        searchResult.setSuccess(CollectionUtils.isEmpty(updateBody.getFailureIds()));
        return searchResult;
    }
}
