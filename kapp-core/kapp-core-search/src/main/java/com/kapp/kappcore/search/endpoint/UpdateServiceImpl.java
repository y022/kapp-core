package com.kapp.kappcore.search.endpoint;

import com.kapp.kappcore.search.common.ExtSearchRequest;
import com.kapp.kappcore.search.common.SearchResult;
import com.kapp.kappcore.search.support.factory.impl.ParamFactory;
import com.kapp.kappcore.search.support.factory.impl.UpdateRequestFactory;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.model.response.UpdateBody;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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
    public SearchResult<UpdateBody> deleteById(ExtSearchRequest extSearchRequest, String indexName) throws IOException {
        SearchParam searchParam = ParamFactory.instance().create(extSearchRequest);
        return doUpdate(searchParam);
    }

    @Override
    public SearchResult<UpdateBody> update(Map<String, Object> data, String indexName, Consumer<Object> consumer) throws IOException {
        return update_bulk(List.of(data), indexName);
    }

    @Override
    public SearchResult<UpdateBody> update_bulk(List<Map<String, Object>> data, String indexName) throws IOException {
        SearchParam searchParam = toParam(data, indexName);
        return doUpdate(searchParam);
    }

    @Override
    public void update_async(Map<String, Object> data, String indexName) throws IOException {
        update_bulk(List.of(data), indexName);
    }

    @Override
    public void update_async(List<Map<String, Object>> data, String indexName, Consumer<Object> consumer) {
        SearchParam searchParam = toParam(data, indexName);
        doUpdate_async(searchParam, consumer);
    }

    @Override
    public void update(ExtSearchRequest extSearchRequest, Consumer<Object> consumer) {
        SearchParam searchParam = ParamFactory.instance().create(extSearchRequest);
        doUpdate_async(searchParam, consumer);
    }

    private void doUpdate_async(SearchParam searchParam, Consumer<Object> callback) {
        ActionRequest request = updateRequestFactory.create(searchParam);
        restHighLevelClient.bulkAsync((BulkRequest) request, RequestOptions.DEFAULT, new ActionListener<>() {
            @Override
            public void onResponse(BulkResponse bulkItemResponses) {
                callback.accept(bulkItemResponses);
            }
            @Override
            public void onFailure(Exception e) {
                LOG.error("update error:", e);
            }
        });
    }

    private SearchResult<UpdateBody> doUpdate(SearchParam searchParam) throws IOException {
        ActionRequest request = updateRequestFactory.create(searchParam);
        try {
            BulkResponse response = restHighLevelClient.bulk((BulkRequest) request, RequestOptions.DEFAULT);
            return collectUpdate(response);
        } catch (IOException e) {
            LOG.error("update error", e);
            throw e;
        }
    }
}
