package com.kapp.kappcore.search.core.interceptor;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.support.model.SearchLimiter;
import com.kapp.kappcore.search.support.model.condition.ValCondition;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import org.apache.commons.lang3.StringUtils;

/**
 * Author:Heping
 * Date: 2024/6/25 13:28
 * 检索参数拦截器
 * 检查参数，并如有必要会进行自动补偿
 */
public class KappSearchParamInterceptor implements SearchInterceptor {
    @Override
    public int order() {
        return 0;
    }

    @Override
    public void intercept(SearchParam searchParam) throws SearchException {
        if (searchParam == null) {
            throw new SearchException(ExCode.search_condition_error, "search param is null!");
        }
        //如果关闭了参数拦截检查
        if (!searchParam.isParamCheck()) {
            return;
        }

        String index = searchParam.getIndex();
        if (StringUtils.isBlank(index)) {
            throw new SearchException(ExCode.search_condition_error, "index is empty!");
        }
        ValCondition condition = searchParam.getCondition();
        if (condition == null) {
            throw new SearchException(ExCode.search_condition_error, "search condition is null!");
        } else {
            condition.validate();
        }
        SearchLimiter searchLimiter = searchParam.getSearchLimiter();
        if (searchLimiter != null) {
            searchLimiter.validate();
        } else {
            throw new SearchException(ExCode.search_condition_error, "search limiter is null!");
        }
    }
}
