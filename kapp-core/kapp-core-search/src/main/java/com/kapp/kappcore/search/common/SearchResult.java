package com.kapp.kappcore.search.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.model.response.Body;
import lombok.Data;

/**
 * Author:Heping
 * Date: 2024/6/21 21:41
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResult<T extends Body> {
    private boolean success;
    private long took;
    private long total;
    private int pageNum;
    private int pageSize;
    private String scrollId;
    private SearchParam searchParam;
    private T data;

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

    public SearchResult(SearchParam searchParam, T data) {
        SearchResult<T> result = new SearchResult<>(data);
        result.setSearchParam(searchParam);
        result.success = true;
    }

    public void pageParam(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public void searchParam(SearchParam searchParam) {
        this.searchParam = searchParam;
    }
}
