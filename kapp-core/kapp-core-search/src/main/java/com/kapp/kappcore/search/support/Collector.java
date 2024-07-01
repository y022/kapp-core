package com.kapp.kappcore.search.support;

import org.elasticsearch.action.search.SearchResponse;

/**
 * Author:Heping
 * Date: 2024/6/27 16:42
 */

public interface Collector<R> {
     R collect(SearchResponse response);
}
