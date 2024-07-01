package com.kapp.kappcore.search.service;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.common.SearchRequestDTO;
import com.kapp.kappcore.search.common.SearchResult;
import com.kapp.kappcore.search.core.Searcher;
import com.kapp.kappcore.search.support.builder.impl.ParamConstructor;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Author:Heping
 * Date: 2024/6/25 17:58
 */
@Slf4j
@Service
public class SearchService {
    private final Searcher searcher;

    public SearchService(Searcher searcher) {
        this.searcher = searcher;
    }

    public SearchResult<?> search(SearchRequestDTO searchRequestDTO) throws SearchException {
        SearchParam searchParam = ParamConstructor.instance().create(searchRequestDTO);
        return searcher.search(searchParam, response -> new SearchResult<>());
    }
}
