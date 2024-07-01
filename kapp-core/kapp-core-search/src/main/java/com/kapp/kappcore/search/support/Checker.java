package com.kapp.kappcore.search.support;

import com.kapp.kappcore.model.exception.SearchException;

/**
 * Author:Heping
 * Date: 2024/6/25 11:00
 */
public interface Checker {
    void checkAndCompensate() throws SearchException;
}
