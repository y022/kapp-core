package com.kapp.kappcore.search.common;

import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.model.response.Body;
import lombok.Data;

/**
 * Author:Heping
 * Date: 2024/6/21 21:41
 */
@Data
public class SearchResult<T extends Body> {
    private boolean success;
    private long took;
    private long total;
    private int pageNum;
    private T data;
    private SearchParam param;

    public SearchResult() {
    }

    public SearchResult(boolean success) {
        this.success = success;
    }

    public SearchResult(T data) {
        this.data = data;
        this.total = data.total();
        this.took = data.took();
    }

    public SearchResult(SearchParam param, T data) {
        SearchResult<T> result = new SearchResult<>(data);
        result.setParam(param);
        result.success = true;
    }
}
