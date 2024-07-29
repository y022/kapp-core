package com.kapp.kappcore.search.support.model.param;

import com.kapp.kappcore.search.support.DateTool;

import java.util.UUID;

public interface SearchMetrics {
    void searchId(String searchId);
    void startTime(String timestamp);
    void endTime(String timestamp);
    default void baseMetrics() {
        searchId(UUID.randomUUID().toString());
        startTime(DateTool.now());
    }

}
