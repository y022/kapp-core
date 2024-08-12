package com.kapp.kappcore.search.endpoint;

import com.kapp.kappcore.search.common.ExtSearchRequest;
import com.kapp.kappcore.search.common.SearchResult;
import com.kapp.kappcore.search.support.SearchCollector;
import com.kapp.kappcore.search.support.factory.impl.ParamFactory;
import com.kapp.kappcore.search.support.model.condition.UpdateCondition;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.model.response.UpdateBody;
import com.kapp.kappcore.search.support.option.DocOption;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.bulk.BulkResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Author:Heping
 * Date: 2024/7/26 17:05
 */
public interface KappDockUpdater {
    SearchResult<UpdateBody> deleteById(ExtSearchRequest extSearchRequest, String indexName) throws IOException;

    SearchResult<UpdateBody> update(Map<String, Object> data, String indexName, Consumer<Object> consumer) throws IOException;

    SearchResult<UpdateBody> update_bulk(List<Map<String, Object>> data, String indexName) throws IOException;

    void update_async(Map<String, Object> data, String indexName) throws IOException;

    void update_async(List<Map<String, Object>> data, String indexName, Consumer<Object> consumer);

    void update(ExtSearchRequest extSearchRequest, Consumer<Object> consumer);

    default SearchCollector<BulkResponse, SearchResult<UpdateBody>> updateCollector() {
        return (response) -> {
            SearchResult<UpdateBody> searchResult = new SearchResult<>();
            UpdateBody ub = new UpdateBody();
            ub.setTook(response.getTook().getMillis());
            ub.setTotal(response.getItems().length);
            response.iterator().forEachRemaining(item -> ub.wire(item.getId(), !item.isFailed()));
            searchResult.setSuccess(CollectionUtils.isEmpty(ub.getFailureIds()));
            return searchResult;
        };
    }

    default SearchParam toParam(List<Map<String, Object>> data, String indexNames) {
        SearchParam searchParam = ParamFactory.instance().create();
        searchParam.setIndex(indexNames);
        UpdateCondition updateCondition = new UpdateCondition(indexNames, DocOption.UPDATE, data);
        searchParam.setCondition(updateCondition);
        searchParam.setSearchId(UUID.randomUUID().toString());
        searchParam.setParamCheck(true);
        return searchParam;
    }

}
