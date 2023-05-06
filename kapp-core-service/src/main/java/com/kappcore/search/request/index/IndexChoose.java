package com.kappcore.search.request.index;

import org.elasticsearch.action.index.IndexRequest;

public interface IndexChoose {
    /**
     * 根据标签来确定使用的index
     *
     * @param tag
     * @return
     */
    IndexRequest index(String tag);
}
