package com.kapp.kappcore.search.support;

import org.elasticsearch.action.ActionResponse;

/**
 * Author:Heping
 * Date: 2024/8/12 14:43
 * search result collector
 */
public interface SearchCallBack<T extends ActionResponse, R> {
    R process(T t);
}
