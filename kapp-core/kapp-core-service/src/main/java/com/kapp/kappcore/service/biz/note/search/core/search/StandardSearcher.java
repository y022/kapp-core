package com.kapp.kappcore.service.biz.note.search.core.search;

import com.kapp.kappcore.model.biz.domain.search.SearchResult;
import com.kapp.kappcore.service.biz.note.SearchControl;
import com.kapp.kappcore.service.biz.note.search.context.SearchContext;

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
    SearchResult<?> search(SearchContext context);

    SearchResult<?> group(SearchContext context);
}
