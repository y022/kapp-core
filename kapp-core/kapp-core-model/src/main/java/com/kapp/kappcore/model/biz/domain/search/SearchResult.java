package com.kapp.kappcore.model.biz.domain.search;

import com.kapp.kappcore.model.biz.Sch;
import lombok.Data;

import java.util.List;
@Data
public class SearchResult<T extends Sch> implements SearchTemplate<T> {
    private long total;
    private long took;
    private List<T> items;

    public SearchResult() {
    }

    private void setTotal(long total) {
        this.total = total;
    }

    private void setTook(long took) {
        this.took = took;
    }

    public SearchResult(long took, long total) {
        this.took = took;
        this.total = total;
    }

    private void setItems(List<T> items) {
        this.items = items;
    }


    @Override
    public long total() {
        return getTotal();
    }

    @Override
    public long took() {
        return getTook();
    }

    @Override
    public void warp(List<T> sch) {
        setItems(sch);
    }

    @Override
    public List<T> searchBody() {
        return items;
    }
}
