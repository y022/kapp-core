package com.kapp.kappcore.model.tool;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;

/**
 * Author:Heping
 * Date: 2024/6/24 0:05
 */
public class CheckUtil {

    public static void checkNotNull(Object obj) {
        if (obj == null) {
            throw new SearchException(ExCode.error, "can not null!");
        }

    }
}
