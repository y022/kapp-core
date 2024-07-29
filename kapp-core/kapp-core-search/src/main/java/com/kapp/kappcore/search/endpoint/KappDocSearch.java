package com.kapp.kappcore.search.endpoint;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.common.SearchRequestDTO;
import com.kapp.kappcore.search.common.SearchResult;

/**
 * Author:Heping
 * Date: 2024/7/26 17:33
 */
public interface KappDocSearch {
    SearchResult<?> search(SearchRequestDTO searchRequestDTO) throws SearchException;
}
