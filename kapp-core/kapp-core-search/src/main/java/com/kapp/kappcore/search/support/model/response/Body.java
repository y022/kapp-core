package com.kapp.kappcore.search.support.model.response;

import java.util.List;

/**
 * Author:Heping
 * Date: 2024/6/23 16:21
 */
public interface Body {
    long took();
    long total();
    List<?> data();
}
