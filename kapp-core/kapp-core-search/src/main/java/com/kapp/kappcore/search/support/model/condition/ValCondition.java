package com.kapp.kappcore.search.support.model.condition;

import com.kapp.kappcore.model.Readable;
import com.kapp.kappcore.search.support.Checker;
import com.kapp.kappcore.search.support.option.DocOption;

import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/6/23 17:10
 */
public interface ValCondition extends Readable, Checker {
    DocOption option();
    Set<String> index();
}
