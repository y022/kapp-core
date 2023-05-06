package com.kappcore.search.core;

import com.kappcore.domain.map.SearchResult;
import com.kappcore.search.context.SearchContext;

public interface Searcher extends SearchControl{
    /**
     * 发起检索动作
     * @param context 检索上下文
     * @return
     */
    SearchResult search(SearchContext context);

}
