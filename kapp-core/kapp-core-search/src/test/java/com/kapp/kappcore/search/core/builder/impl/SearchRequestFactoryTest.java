package com.kapp.kappcore.search.core.builder.impl;

import com.kapp.kappcore.search.support.factory.impl.SearchRequestFactory;
import com.kapp.kappcore.search.support.model.SearchLimiter;
import com.kapp.kappcore.search.support.model.condition.SearchCondition;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.option.DocOption;
import com.kapp.kappcore.search.support.option.sort.SortRule;
import org.elasticsearch.action.search.SearchRequest;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Author:Heping
 * Date: 2024/6/30 17:46
 */
public class SearchRequestFactoryTest {

    @Test
    public void testSearch() {
        SearchRequestFactory instance = SearchRequestFactory.getInstance();


        SearchParam searchParam = new SearchParam();

        SearchCondition searchCondition = new SearchCondition();
        HashMap<String, Object> keyValueMap = new HashMap<>();
        keyValueMap.put("title", "你好");
        searchCondition.setSearchValueMap(keyValueMap);
        searchCondition.setOption(DocOption.SEARCH);
        searchCondition.setIndex("book");

        searchParam.setCondition(searchCondition);

        SearchLimiter searchLimiter = new SearchLimiter();
        searchLimiter.setPageSize(10);
        searchLimiter.setPageNum(1);
        searchLimiter.setSortRule(SortRule.SCORE);

        searchParam.setSearchLimiter(searchLimiter);


        SearchRequest searchRequest = instance.create(searchParam);
        assertNotNull(searchRequest);


    }
}