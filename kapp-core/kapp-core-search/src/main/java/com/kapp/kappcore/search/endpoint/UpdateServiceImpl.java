package com.kapp.kappcore.search.endpoint;

import com.kapp.kappcore.search.common.SearchResult;
import com.kapp.kappcore.search.support.factory.impl.ParamConstructor;
import com.kapp.kappcore.search.support.factory.impl.UpdateRequestFactory;
import com.kapp.kappcore.search.support.model.condition.UpdateCondition;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.model.response.UpdateBody;
import com.kapp.kappcore.search.support.option.DocOption;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Author:Heping
 * Date: 2024/6/30 19:40
 */
public class UpdateServiceImpl implements KappDockUpdate {
    private static final Logger LOG = LoggerFactory.getLogger(UpdateServiceImpl.class);
    private final RestHighLevelClient restHighLevelClient;
    private final UpdateRequestFactory updateRequestFactory;

    public UpdateServiceImpl(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
        this.updateRequestFactory = UpdateRequestFactory.getInstance();
    }

    @Override
    public SearchResult<UpdateBody> update(Map<String, Object> data, String indexName) {
        return update_bulk(List.of(data), indexName);
    }


    @Override
    public SearchResult<UpdateBody> update_bulk(List<Map<String, Object>> data, String indexName) {
        Set<String> indexNames = Set.of(indexName);
        SearchParam searchParam = toParam(data, indexNames);
        ActionRequest request = updateRequestFactory.create(searchParam);
        SearchResult<UpdateBody> searchResult;
        try {
            searchResult = collectUpdate(restHighLevelClient.bulk((BulkRequest) request, RequestOptions.DEFAULT));
        } catch (Exception e) {
            LOG.error("update error", e);
            return new SearchResult<>(false);
        }
        return searchResult;
    }

    @Override
    public void update_async(Map<String, Object> data, String indexName) {
        update_bulk(List.of(data), indexName);
    }

    @Override
    public void update_async(List<Map<String, Object>> data, String indexName) {
        Set<String> indexNames = Set.of(indexName);
        SearchParam searchParam = toParam(data, indexNames);
        ActionRequest request = updateRequestFactory.create(searchParam);
        restHighLevelClient.bulkAsync((BulkRequest) request, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
            @Override
            public void onResponse(BulkResponse bulkItemResponses) {
                LOG.debug("update success!");
            }
            @Override
            public void onFailure(Exception e) {
                LOG.error("update error:", e);
            }
        });
    }

    private SearchParam toParam(List<Map<String, Object>> data, Set<String> indexNames) {
        SearchParam searchParam = ParamConstructor.instance().create();
        searchParam.setIndexes(indexNames);
        UpdateCondition updateCondition = new UpdateCondition(indexNames, DocOption.UPDATE, data);
        searchParam.setCondition(updateCondition);
        searchParam.setSearchId(UUID.randomUUID().toString());
        searchParam.setParamCheck(true);
        return searchParam;
    }

}
