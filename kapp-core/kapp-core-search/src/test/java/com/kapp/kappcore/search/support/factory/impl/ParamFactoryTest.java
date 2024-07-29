package com.kapp.kappcore.search.support.factory.impl;

import com.kapp.kappcore.search.common.ExtSearchRequest;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.option.DocOption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

class ParamFactoryTest {

    private static final Logger LOG = LoggerFactory.getLogger(ParamFactoryTest.class);

    @Test
    void create() {
        ExtSearchRequest extSearchRequest = new ExtSearchRequest();
        extSearchRequest.setDeleteIds(Set.of("xxxx", "22222"));
        extSearchRequest.setDocOption(DocOption.DELETE.getCode());
        SearchParam searchParam = ParamFactory.instance().create(extSearchRequest);
        Assertions.assertNotNull(searchParam);
        LOG.info(searchParam.toString());

    }
}