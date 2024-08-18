package com.kapp.kappcore.search.support.factory.impl;

import com.kapp.kappcore.search.support.factory.SearchFactory;
import com.kapp.kappcore.search.support.factory.UpdateRequestBuilder;
import com.kapp.kappcore.search.support.model.condition.UpdateCondition;
import com.kapp.kappcore.search.support.model.condition.ValCondition;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.option.DocOption;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionRequest;

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
        UpdateRequestBuilder instance = UpdateRequestBuilder.getInstance();

        DocOption option = condition.option();
        String indexName = searchParam.getSearchIndex();
        ActionRequest actionRequest = null;
        switch (option) {
            case ADD:
            case UPDATE:
                actionRequest = instance.upsert(indexName, upCondition.getUpdateMap());
                break;
            case DELETE:
                actionRequest = instance.del(indexName, upCondition.getDelIds());
                break;
            case DELETE_BY_QUERY:
                actionRequest = instance.delByQuery(indexName, upCondition.getQueryBuilder());
                break;
        }
        return actionRequest;
    }


}
