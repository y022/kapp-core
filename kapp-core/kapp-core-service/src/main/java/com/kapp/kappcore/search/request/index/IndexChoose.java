package com.kapp.kappcore.search.request.index;

import org.elasticsearch.action.index.IndexRequest;

public interface IndexChoose {
    /**
     * 根据标签来确定使用的index
     *
     * @param tag 标签
     * @return 索引
     */
    IndexRequest index(String tag);
}
