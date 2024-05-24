package com.kapp.kappcore.model.biz;

import java.util.List;

public interface SearchTemplate<T extends Sch> {
    long total();
    long took();
    void warp(List<T> sch);
    List<T> searchBody();
}
