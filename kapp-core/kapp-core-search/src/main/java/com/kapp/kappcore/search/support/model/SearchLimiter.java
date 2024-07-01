package com.kapp.kappcore.search.support.model;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.support.Checker;
import com.kapp.kappcore.search.support.model.param.ViewParam;
import com.kapp.kappcore.search.support.option.ViewType;
import com.kapp.kappcore.search.support.option.sort.SortRule;
import com.kapp.kappcore.search.support.option.sort.SortType;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/6/24 22:25
 * 搜索限制：分页，排序，响应字段
 */
@Data
public class SearchLimiter implements Checker {
    private int pageNum;
    private int pageSize;
    private Set<String> sortKeys;
    private SortRule sortRule;
    private SortType sortType;
    private ViewParam viewParam;
    private boolean showScore = true;

    public SearchLimiter(Set<String> sortKeys, SortRule sortRule, SortType sortType) {
        this.sortKeys = sortKeys;
        this.sortRule = sortRule;
        this.sortType = sortType;
    }

    public SearchLimiter() {
    }

    /**
     * 默认的排序规则
     *
     * @return st
     */
    public static SearchLimiter defaultSort() {
        return new SearchLimiter(Set.of(), SortRule.SCORE, SortType.DESC);
    }

    @Override
    public void checkAndCompensate() throws SearchException {
        if (pageSize > 100) {
            throw new SearchException(ExCode.search_condition_error, "page size to large!");
        }
        if (pageNum > 100) {
            throw new SearchException(ExCode.search_condition_error, "page number to large!");
        }
        if (sortType == null) {
            sortType = SortType.DESC;
        }
        if (sortRule == null) {
            sortRule = SortRule.SCORE;
        } else {
            // 如果是指定字段排序，但是没有给出排序字段，就退化为分数排序
            if (SortRule.FIELD.equals(sortRule)) {
                if (CollectionUtils.isEmpty(sortKeys)) {
                    sortRule = SortRule.SCORE;
                }
            }
        }
        //如果没有给出viewType,或者设置返回指定字段，但没有给出指定字段，则直接报错
        if (viewParam == null) {
            viewParam = ViewParam.defaultView();
        } else if (viewParam.getViewType() == null) {
            viewParam = ViewParam.defaultView();
        } else if (viewParam.getViewType().equals(ViewType.SET)) {
            if (CollectionUtils.isEmpty(viewParam.getViewFields())) {
                throw new SearchException(ExCode.search_condition_error, "please set view field!");
            }
        }
    }
}
