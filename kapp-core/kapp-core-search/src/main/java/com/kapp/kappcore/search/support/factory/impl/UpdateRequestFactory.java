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
        String indexName = upCondition.getActualIndex();
        switch (option) {
            case ADD:
            case UPDATE:
                 return instance.upsert(indexName, upCondition.getUpdateMap());
            case DELETE:
                return instance.del(indexName, upCondition.getDelIds());
            case DELETE_BY_QUERY:
//                return instance.delByQuery(indexName,);

        }
        return null;
    }



}
