package com.kapp.kappcore.search.core.builder;

import com.kapp.kappcore.search.support.builder.impl.SearchRequestFactory;
import com.kapp.kappcore.search.support.DateTool;
import com.kapp.kappcore.search.support.model.SearchLimiter;
import com.kapp.kappcore.search.support.model.condition.SearchCondition;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.model.param.SearchParamUnit;
import com.kapp.kappcore.search.support.option.DocOption;
import com.kapp.kappcore.search.support.option.MultiQueryRule;
import com.kapp.kappcore.search.support.option.sort.SortRule;
import org.elasticsearch.action.search.SearchRequest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
    void search() {
        SearchRequestFactory instance = SearchRequestFactory.getInstance();
        Set<String> indexes = Set.of("fr");
        SearchParam searchParam = new SearchParam();
        searchParam.setIndexes(indexes);
        searchParam.setSearchTime(DateTool.now());
        searchParam.setSearchId("xxxx");
        searchParam.setParamCheck(true);

        SearchCondition searchCondition = new SearchCondition();
        searchCondition.setIndex(indexes);
        searchCondition.setSearchAll(false);
        searchCondition.setOption(DocOption.SEARCH);
        ArrayList<SearchParamUnit> searchParamUnits = new ArrayList<>();
        SearchParamUnit searchParamUnit_1 = new SearchParamUnit();
        searchParamUnit_1.setKey("tag");
        searchParamUnit_1.setValue("fr");
        searchParamUnit_1.setMultiQueryRule(MultiQueryRule.MUST);

        SearchParamUnit searchParamUnit_2 = new SearchParamUnit();
        searchParamUnit_2.setKey("owner");
        searchParamUnit_2.setValue("yiyi");
        searchParamUnit_2.setMultiQueryRule(MultiQueryRule.MUST);

        searchParamUnits.add(searchParamUnit_1);
        searchParamUnits.add(searchParamUnit_2);
        searchCondition.setSearchParamUnits(searchParamUnits);

        searchParam.setCondition(searchCondition);

        SearchLimiter searchLimiter = new SearchLimiter();
        searchLimiter.setSortKeys(Set.of("tag","owner"));
        searchLimiter.setSortRule(SortRule.FIELD);
        searchParam.setSearchLimiter(searchLimiter);

        searchLimiter.setPageNum(1);
        searchLimiter.setPageSize(10);
        SearchRequest searchRequest = instance.create(searchParam);


    }
}