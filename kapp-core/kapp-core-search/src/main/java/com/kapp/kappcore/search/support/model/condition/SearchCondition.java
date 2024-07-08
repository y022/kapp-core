package com.kapp.kappcore.search.support.model.condition;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.support.model.param.HighlightParam;
import com.kapp.kappcore.search.support.model.param.HitParam;
import com.kapp.kappcore.search.support.model.param.SearchParamUnit;
import com.kapp.kappcore.search.support.option.DocOption;
import com.kapp.kappcore.search.support.option.MultiQueryRule;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author:Heping
 * Date: 2024/6/23 16:54
 * need search value
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchCondition extends AbstractSearchCondition {
    /**
     * 是否直接查询所有内容
     */
    private boolean searchAll;
    /**
     * 高亮参数
     */
    private HighlightParam highlightParam;
    /**
     * 直接的key-value嵌套查询。
     */
    private Map<String, Object> searchValueMap;
    /**
     * 多条件
     */
    private List<SearchParamUnit> searchParamUnits;


    public SearchCondition() {
    }

    public static SearchCondition search(Map<String, Object> searchValueMap) {
        SearchCondition condition = new SearchCondition();
        condition.setSearchValueMap(searchValueMap);
        condition.setOption(DocOption.SEARCH);
        return condition;
    }

    public static SearchCondition search(List<SearchParamUnit> searchParamUnits) {
        SearchCondition condition = new SearchCondition();
        condition.setSearchParamUnits(searchParamUnits);
        condition.setOption(DocOption.SEARCH);
        return condition;
    }


    /**
     * 如果外部传入了option,那么就直接使用，否则自行推测
     *
     * @return option
     */
    @Override
    public DocOption option() {
        return DocOption.SEARCH;
    }


    @Override
    public String read() {
        return "";
    }

    @Override
    public void validate() throws SearchException {
        if (!searchAll) {
            if (hasMultiCondition()) {
                if (CollectionUtils.isEmpty(searchParamUnits)) {
                    throw new SearchException(ExCode.search_condition_error, "missing search param!");
                }
            } else if (MapUtils.isEmpty(searchValueMap)) {
                throw new SearchException(ExCode.search_condition_error, "missing search param!");
            }
        }

        if (highlightParam != null) {
            if (highlightParam.isHighlight()) {
                if (StringUtils.isAnyBlank(highlightParam.getPreTag(), highlightParam.getPostTag())) {
                    throw new SearchException(ExCode.search_condition_error, "highlight param are preTag and postTag required!");
                }
            }
        } else {
            highlightParam = HighlightParam.instance();
        }

    }

    @Override
    boolean searchAll() {
        return searchAll;
    }

    @Override
    public boolean hasMultiCondition() {
        return CollectionUtils.isNotEmpty(getSearchParamUnits());
    }

    /**
     * 转换为最小检索单位
     */
    public List<SearchParamUnit> toParamUnit() {
        return MapUtils.isEmpty(searchValueMap) ? List.of() :
                searchValueMap.entrySet().stream().map(entry -> {
                    SearchParamUnit searchParamUnit = new SearchParamUnit();
                    searchParamUnit.setKey(entry.getKey());
                    searchParamUnit.setValue(entry.getValue());
                    searchParamUnit.setHitParam(HitParam.accurate());
                    searchParamUnit.setMultiQueryRule(MultiQueryRule.MUST);
                    return searchParamUnit;
                }).collect(Collectors.toList());
    }

}
