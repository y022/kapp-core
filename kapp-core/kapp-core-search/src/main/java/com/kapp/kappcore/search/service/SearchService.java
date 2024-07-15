package com.kapp.kappcore.search.service;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.common.SearchRequestDTO;
import com.kapp.kappcore.search.common.SearchResult;
import com.kapp.kappcore.search.core.SearchActor;
import com.kapp.kappcore.search.support.factory.impl.ParamConstructor;
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
    private final SearchActor searchActor;

    public SearchService(SearchActor searchActor) {
        this.searchActor = searchActor;
    }

    public SearchResult<?> search(SearchRequestDTO searchRequestDTO) throws SearchException {
        SearchParam searchParam = ParamConstructor.instance().create(searchRequestDTO);
        return searchActor.search(searchParam, response -> new SearchResult<>());
    }
}
