package com.kapp.kappcore.search.core.update;

import com.kapp.kappcore.search.support.factory.impl.UpdateRequestFactory;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

/**
 * Author:Heping
 * Date: 2024/7/15 18:01
 */
@Slf4j
@Component
public class KappDocUpdater implements DocUpdate {
    private final UpdateRequestFactory updateRequestFactory;
    private final RestHighLevelClient restHighLevelClient;

    public KappDocUpdater(UpdateRequestFactory updateRequestFactory, RestHighLevelClient restHighLevelClient) {
        this.updateRequestFactory = updateRequestFactory;
        this.restHighLevelClient = restHighLevelClient;
    }


    @Override
    public void update(SearchParam searchParam) {
        ActionRequest actionRequest = updateRequestFactory.create(searchParam);
        BulkResponse response = null;
        try {
            response = restHighLevelClient.bulk((BulkRequest) actionRequest, RequestOptions.DEFAULT);
        } catch (Exception exception) {
            String exceptionMessage = "";
            if (response != null) {
                exceptionMessage = response.buildFailureMessage();
            }
            log.error("update error:{}", exceptionMessage, exception);
        }
    }
}
