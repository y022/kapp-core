package com.kapp.kappcore.search.common;

import com.kapp.kappcore.search.support.model.condition.SearchCondition;
import com.kapp.kappcore.search.support.model.param.GroupParamUnit;
import com.kapp.kappcore.search.support.model.param.HitParam;
import com.kapp.kappcore.search.support.model.param.SearchParamUnit;
import com.kapp.kappcore.search.support.option.ContHitStrategy;
import com.kapp.kappcore.search.support.option.DocOption;
import com.kapp.kappcore.search.support.option.sort.SortRule;
import com.kapp.kappcore.search.support.option.sort.SortType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/6/25 10:13
 */
@Data
@NoArgsConstructor
public class SearchRequestDTO {
    private Set<String> tag;
    private int pageNum;
    private int pageSize;
    /**
     * @see DocOption
     */
    private String docOption;
    /**
     * @see SortType
     */
    private String sortType;
    /**
     * @see SortRule
     */
    private String sortRule;
    /**
     * 用于排序的key
     */
    private Set<String> sortKeys;
    /**
     * @see ContHitStrategy
     */
    private String contHitStrategy;
    /**
     * @see HitParam
     */
    private String wildcard;
    /**
     * @see SearchCondition
     */
    private Map<String, Object> searchValueMap;
    /**
     * @see SearchCondition
     */
    private List<SearchParamUnit> searchParamUnits;
    private List<GroupParamUnit> groupParamUnits;
    /**
     * 是否高亮
     */
    private boolean highlight;
    /**
     * 字段响应方式
     */
    private String viewType;
    private Set<String> viewFields;
    /**
     * 响应是否返回分数
     */
    private boolean showScore;
    private boolean searchAll;


}
