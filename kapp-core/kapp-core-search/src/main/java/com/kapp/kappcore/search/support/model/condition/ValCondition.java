package com.kapp.kappcore.search.support.model.condition;

import com.kapp.kappcore.model.Readable;
import com.kapp.kappcore.search.support.Validate;
import com.kapp.kappcore.search.support.option.DocOption;

/**
 * Author:Heping
 * Date: 2024/6/23 17:10
 */
public interface ValCondition extends Readable, Validate {
    DocOption option();

    String index();

    String index(String index);
}
