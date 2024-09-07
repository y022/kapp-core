package com.kapp.kappcore.search.support.model;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.model.exception.ValidateException;
import com.kapp.kappcore.search.support.Validate;
import com.kapp.kappcore.search.support.model.param.SortParam;
import com.kapp.kappcore.search.support.model.param.ViewParam;
import com.kapp.kappcore.search.support.option.ViewType;
import com.kapp.kappcore.search.support.option.sort.SortRule;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * Author:Heping
 * Date: 2024/6/24 22:25
 * 搜索限制：分页，排序，响应字段
 */
@Data
public class SearchLimiter implements Validate {
    private int pageNum;
    private int pageSize;
    private SortRule sortRule;
    private List<SortParam> sortParams;
    private ViewParam viewParam;
    private boolean showScore = true;

    public SearchLimiter() {
    }


    @Override
    public void validate() throws ValidateException {
        if (pageSize > 100) {
            throw new ValidateException(ExCode.search_condition_error, "page size to large!");
        }
        if (pageNum > 100) {
            throw new ValidateException(ExCode.search_condition_error, "page number to large!");
        }
        if (sortRule == null) {
            sortRule = SortRule.SCORE;
            sortParams = List.of();
        } else {
            // 如果是指定字段排序，但是没有给出排序字段，就退化为分数排序
            if (SortRule.FIELD.equals(sortRule)) {
                if (CollectionUtils.isEmpty(sortParams)) {
                    sortRule = SortRule.SCORE;
                }
            }
        }
        //如果没有给出viewType,或者设置了返回指定字段，但没有给出指定字段，则直接报错
        if (viewParam == null) {
            viewParam = ViewParam.defaultView();
        } else if (viewParam.getViewType() == null) {
            viewParam = ViewParam.defaultView();
        } else if (viewParam.getViewType().equals(ViewType.SET)) {
            if (CollectionUtils.isEmpty(viewParam.getViewFields())) {
                throw new ValidateException(ExCode.search_condition_error, "please set view field!");
            }
        }
    }
}
