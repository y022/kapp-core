package com.kapp.kappcore.search.support.builder.impl;

import com.kapp.kappcore.search.support.builder.SearchFactory;
import com.kapp.kappcore.search.support.model.condition.ValCondition;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.option.DocOption;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionRequest;

import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/6/30 18:41
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRequestFactory implements SearchFactory<SearchParam, ActionRequest> {
    public static final UpdateRequestFactory INSTANCE = new UpdateRequestFactory();

    @Override
    public ActionRequest create(SearchParam searchParam) {
        ValCondition condition = searchParam.getCondition();
        DocOption option = condition.option();
        Set<String> index = condition.index();



        return null;
    }
}
