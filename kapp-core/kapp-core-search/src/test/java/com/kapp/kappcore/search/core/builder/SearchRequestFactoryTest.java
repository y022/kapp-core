package com.kapp.kappcore.search.core.builder;

import com.kapp.kappcore.search.support.DateTool;
import com.kapp.kappcore.search.support.builder.impl.SearchRequestFactory;
import com.kapp.kappcore.search.support.model.SearchLimiter;
import com.kapp.kappcore.search.support.model.condition.SearchCondition;
import com.kapp.kappcore.search.support.model.param.*;
import com.kapp.kappcore.search.support.option.DocOption;
import com.kapp.kappcore.search.support.option.MultiQueryRule;
import com.kapp.kappcore.search.support.option.ViewType;
import com.kapp.kappcore.search.support.option.sort.SortRule;
import com.kapp.kappcore.search.support.option.sort.SortType;
import org.elasticsearch.action.search.SearchRequest;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/6/27 23:32
 */
class SearchRequestFactoryTest {

    @Test
    void getInstance() {
    }

    @Test
    void create() {
    }

    @Test
    void testCreate() {
    }

    @Test
    void group() {
    }

    @Test
    void search_v1() {
        SearchRequestFactory instance = SearchRequestFactory.getInstance();
        Set<String> indexes = Set.of("fr");
        SearchParam searchParam = new SearchParam();
        searchParam.setIndexes(indexes);
        searchParam.setSearchTime(DateTool.now());
        searchParam.setSearchId("xxxx");
        searchParam.setParamCheck(true);

        HitParam accurate = HitParam.accurate();

        SearchCondition searchCondition = new SearchCondition();
        searchCondition.setIndex(indexes);
        searchCondition.setSearchAll(false);
        searchCondition.setOption(DocOption.SEARCH);
        ArrayList<SearchParamUnit> searchParamUnits = new ArrayList<>();
        SearchParamUnit searchParamUnit_1 = new SearchParamUnit();
        searchParamUnit_1.setKey("tag");
        searchParamUnit_1.setValue("fr");
        searchParamUnit_1.setMultiQueryRule(MultiQueryRule.MUST);
        searchParamUnit_1.setHitParam(accurate);

        SearchParamUnit searchParamUnit_2 = new SearchParamUnit();
        searchParamUnit_2.setKey("owner");
        searchParamUnit_2.setValue("yiyi");
        searchParamUnit_2.setMultiQueryRule(MultiQueryRule.MUST_NOT);

        searchParamUnit_2.setHitParam(accurate);

        searchParamUnits.add(searchParamUnit_1);
        searchParamUnits.add(searchParamUnit_2);
        searchCondition.setSearchParamUnits(searchParamUnits);

        searchParam.setCondition(searchCondition);

        SearchLimiter searchLimiter = new SearchLimiter();
        searchLimiter.setSortRule(SortRule.FIELD);
        searchParam.setSearchLimiter(searchLimiter);

        searchLimiter.setPageNum(1);
        searchLimiter.setPageSize(10);
        searchLimiter.setViewParam(ViewParam.defaultView());

        SearchRequest searchRequest = instance.create(searchParam);
        Assertions.assertNotNull(searchRequest);

    }

    @Test
    void search_v2() {
        SearchRequestFactory instance = SearchRequestFactory.getInstance();
        Set<String> indexes = Set.of("fr");
        SearchParam searchParam = new SearchParam();
        searchParam.setIndexes(indexes);
        searchParam.setSearchTime(DateTool.now());
        searchParam.setSearchId("xxxx");
        searchParam.setParamCheck(true);

        HitParam accurate = HitParam.accurate();

        SearchCondition searchCondition = new SearchCondition();
        searchCondition.setIndex(indexes);
        searchCondition.setSearchAll(false);
        searchCondition.setOption(DocOption.SEARCH);
        ArrayList<SearchParamUnit> searchParamUnits = new ArrayList<>();
        SearchParamUnit searchParamUnit_1 = new SearchParamUnit();
        searchParamUnit_1.setKey("tag");
        searchParamUnit_1.setValue("fr");
        searchParamUnit_1.setMultiQueryRule(MultiQueryRule.MUST);
        searchParamUnit_1.setHitParam(accurate);

        SearchParamUnit searchParamUnit_2 = new SearchParamUnit();
        searchParamUnit_2.setKey("owner");
        searchParamUnit_2.setValue("yiyi");
        searchParamUnit_2.setMultiQueryRule(MultiQueryRule.MUST_NOT);

        searchParamUnit_2.setHitParam(accurate);

        searchParamUnits.add(searchParamUnit_1);
        searchParamUnits.add(searchParamUnit_2);
        searchCondition.setSearchParamUnits(searchParamUnits);

        searchParam.setCondition(searchCondition);

        SearchLimiter searchLimiter = new SearchLimiter();
        searchLimiter.setSortRule(SortRule.FIELD);
        ArrayList<SortParam> sortParams = new ArrayList<>();
        SortParam sortParam = new SortParam();
        sortParam.setSortKey("date");
        sortParam.setSortType(SortType.DESC);
        sortParams.add(sortParam);
        searchLimiter.setSortParams(sortParams);

        searchParam.setSearchLimiter(searchLimiter);

        searchLimiter.setPageNum(1);
        searchLimiter.setPageSize(10);
        searchLimiter.setViewParam(ViewParam.defaultView());

        SearchRequest searchRequest = instance.create(searchParam);
        Assertions.assertNotNull(searchRequest);

    }
}