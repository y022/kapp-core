package com.kapp.kappcore.search.core;

import com.kapp.kappcore.search.context.SearchContext;
import com.kapp.kappcore.search.support.result.SearchResult;

/**
 * 标准检索器
 */
public interface StandardSearcher extends SearchControl {
    /**
     * 发起检索动作
     *
     * @param context 检索上下文
     * @return 检索结果
     */
    SearchResult search(SearchContext context);

}
