package com.kapp.kappcore.search.endpoint;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
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
    public SearchResult<UpdateBody> deleteById(ExtSearchRequest extSearchRequest, String indexName) {
        SearchParam searchParam = ParamFactory.instance().create(extSearchRequest);
        doUpdate(searchParam);
        SearchResult<UpdateBody> result = new SearchResult<>();
        result.setData(new UpdateBody() {{
            setSuccessIds(extSearchRequest.getDeleteIds());
        }});

        return result;
    }

    @Override
    public SearchResult<UpdateBody> update(Map<String, Object> data, String indexName, Consumer<Object> consumer) {
        return update_bulk(List.of(data), indexName);
    }

    @Override
    public SearchResult<UpdateBody> update_bulk(List<Map<String, Object>> data, String indexName) {
        SearchParam searchParam = toParam(data, indexName);
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
    public void update_async(List<Map<String, Object>> data, String indexName, Consumer<Object> consumer) {
        SearchParam searchParam = toParam(data, indexName);
        doUpdate_Async(searchParam, consumer);
    }

    @Override
    public void update(ExtSearchRequest extSearchRequest, Consumer<Object> consumer) {
        SearchParam searchParam = ParamFactory.instance().create(extSearchRequest);
        doUpdate_Async(searchParam, consumer);
    }

    private void doUpdate_Async(SearchParam searchParam, Consumer<Object> callback) {
        ActionRequest request = updateRequestFactory.create(searchParam);
        restHighLevelClient.bulkAsync((BulkRequest) request, RequestOptions.DEFAULT, new ActionListener<>() {
            @Override
            public void onResponse(BulkResponse bulkItemResponses) {
                LOG.info("update success!");
            }

            @Override
            public void onFailure(Exception e) {
                LOG.error("update error:", e);
            }
        });
    }

    private void doUpdate(SearchParam searchParam) {
        ActionRequest request = updateRequestFactory.create(searchParam);
        try {
            BulkResponse response = restHighLevelClient.bulk((BulkRequest) request, RequestOptions.DEFAULT);
            if (response.hasFailures()) {
                throw new SearchException(ExCode.search_server_error, "update error!");
            }
        } catch (Exception e) {

        }
    }
}
