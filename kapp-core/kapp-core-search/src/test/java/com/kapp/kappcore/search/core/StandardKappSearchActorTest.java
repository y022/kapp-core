package com.kapp.kappcore.search.core;

import com.kapp.kappcore.search.BaseServerConnectorTestCase;
import com.kapp.kappcore.search.common.ExtSearchRequest;
import com.kapp.kappcore.search.core.search.StandardKappSearchActor;
import com.kapp.kappcore.search.support.factory.impl.ParamFactory;
import com.kapp.kappcore.search.support.model.param.GroupParamUnit;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.option.ContHitStrategy;
import com.kapp.kappcore.search.support.option.DocOption;
import com.kapp.kappcore.search.support.option.GroupOption;
import com.kapp.kappcore.search.support.option.ViewType;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.elasticsearch.action.search.SearchResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Author:Heping
 * Date: 2024/6/27 11:25
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class StandardKappSearchActorTest extends BaseServerConnectorTestCase {
    private static StandardKappSearchActor searcher;

    @BeforeAll
    public static void setup() {
        initConnector();
        searcher = new StandardKappSearchActor(restHighLevelClient, null);
    }

    @AfterAll
    public static void close() {
        closeConnector();
    }

    private static SearchResponse collect(SearchResponse response) {
        log.info("result:{}", response.toString());
        return null;
    }

    @Test
    void search() {
        ExtSearchRequest extSearchRequest = new ExtSearchRequest();
        extSearchRequest.setSearchAll(false);
        extSearchRequest.setIndex("book");
        extSearchRequest.setViewType(ViewType.TIP.getCode());
        extSearchRequest.setDocOption(DocOption.GROUP.getCode());
        extSearchRequest.setContHitStrategy(ContHitStrategy.ACCURATE.getCode());

        GroupParamUnit paramUnit = new GroupParamUnit("tag", GroupOption.BUCKET);
        extSearchRequest.setGroupParamUnits(Lists.list(paramUnit));
        extSearchRequest.setPageNum(1);
        extSearchRequest.setPageSize(10);
        ParamFactory paramConstructor = ParamFactory.instance();
        SearchParam searchParam = paramConstructor.create(extSearchRequest);
        searcher.search(searchParam, StandardKappSearchActorTest::collect);
    }

    @Test
    void search_metric() {
        ExtSearchRequest extSearchRequest = new ExtSearchRequest();
        extSearchRequest.setSearchAll(false);
        extSearchRequest.setIndex("book");
        extSearchRequest.setViewType(ViewType.NONE.getCode());
        extSearchRequest.setDocOption(DocOption.GROUP.getCode());
        extSearchRequest.setContHitStrategy(ContHitStrategy.ACCURATE.getCode());

        GroupParamUnit paramUnit_max = new GroupParamUnit("bodyLength", GroupOption.MAX);
        GroupParamUnit paramUnit_min = new GroupParamUnit("bodyLength", GroupOption.MIN);
        GroupParamUnit paramUnit_avg = new GroupParamUnit("bodyLength", GroupOption.AVG);
        GroupParamUnit paramUnit_sum = new GroupParamUnit("bodyLength", GroupOption.SUM);
        GroupParamUnit paramUnit_count = new GroupParamUnit("bodyLength", GroupOption.COUNT);

        extSearchRequest.setGroupParamUnits(Lists.list(paramUnit_max, paramUnit_min, paramUnit_avg, paramUnit_sum, paramUnit_count));
        extSearchRequest.setPageNum(1);
        extSearchRequest.setPageSize(10);
        ParamFactory paramConstructor = ParamFactory.instance();
        SearchParam searchParam = paramConstructor.create(extSearchRequest);
        searcher.search(searchParam, StandardKappSearchActorTest::collect);
    }
}