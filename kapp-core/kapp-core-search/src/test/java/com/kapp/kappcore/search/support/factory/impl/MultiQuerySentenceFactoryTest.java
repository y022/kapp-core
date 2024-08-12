package com.kapp.kappcore.search.support.factory.impl;

import com.kapp.kappcore.search.support.model.SearchLimiter;
import com.kapp.kappcore.search.support.model.condition.SearchCondition;
import com.kapp.kappcore.search.support.model.param.HighlightParam;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.option.DocOption;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

class MultiQuerySentenceFactoryTest {
    private final Logger logger = LoggerFactory.getLogger(MultiQuerySentenceFactoryTest.class);


    @Test
    void query_all() {
        MultiQuerySentenceFactory instance = MultiQuerySentenceFactory.getInstance();
        SearchParam searchParam = defaultSearchParam();

        SearchCondition searchCondition = new SearchCondition();

        searchCondition.setIndex(searchParam.getIndex());
        searchCondition.setSearchAll(true);
        searchCondition.setHighlightParam(HighlightParam.noneHighlight());

        searchParam.setCondition(searchCondition);

        QueryBuilder queryBuilder = instance.create(searchParam);
        Assertions.assertNotNull(queryBuilder);

        print(queryBuilder);
    }

    @Test
    public void query_match() {
        SearchParam searchParam = defaultSearchParam();

        SearchCondition searchCondition = new SearchCondition();

        searchCondition.setIndex(searchParam.getIndex());
        searchCondition.setSearchAll(false);
        searchCondition.setOption(DocOption.SEARCH);

        HashMap<String, Object> searchValueMap = new HashMap<>();
        searchValueMap.put("title", "完美世界");
        searchValueMap.put("tag", "网文");
        searchValueMap.put("content", "石昊");
        searchCondition.setSearchValueMap(searchValueMap);
        searchParam.setCondition(searchCondition);
        SearchLimiter searchLimiter = new SearchLimiter();
        searchLimiter.setPageNum(1);
        searchLimiter.setPageSize(10);
        searchParam.setSearchLimiter(searchLimiter);

        QueryBuilder queryBuilder = MultiQuerySentenceFactory.getInstance().create(searchParam);
        Assertions.assertNotNull(queryBuilder);
        print(queryBuilder);
    }


    public SearchParam defaultSearchParam() {
        SearchParam searchParam = new SearchParam();
        searchParam.setIndex("book");
        searchParam.setParamCheck(false);
        searchParam.setShowParam(false);
        return searchParam;
    }

    public void print(Object t) {
        logger.info("sentence:");
        logger.info(t.toString());

    }
}