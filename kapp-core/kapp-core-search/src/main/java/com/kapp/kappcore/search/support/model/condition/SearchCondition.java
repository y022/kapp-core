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
     * <p>直接的key-value形式查询，会在内部转化为{@link #searchParamUnits},通过{@link #toParamUnit()} </p>
     * <p>使用{@link MultiQueryRule#MUST}组合多个查询条件</p>
     * <p>条件查询方式为{@link com.kapp.kappcore.search.support.option.ContHitStrategy#PARTICIPLE}</p>
     */
    private Map<String, Object> searchValueMap;
    /**
     * 多条件
     */
    private List<SearchParamUnit> searchParamUnits;

    public SearchCondition() {
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
        return this.toString();
    }

    @Override
    public void validate() throws SearchException {
        super.validate();
        if (!searchAll) {
            if (hasMultiCondition()) {
                if (CollectionUtils.isEmpty(searchParamUnits)) {
                    throw new SearchException(ExCode.search_condition_error, "missing multi search param!");
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
            highlightParam = HighlightParam.noneHighlight();
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
        return MapUtils.isEmpty(searchValueMap) ? List.of() : searchValueMap.entrySet().stream().map(entry -> {
            SearchParamUnit searchParamUnit = new SearchParamUnit();
            searchParamUnit.setKey(entry.getKey());
            searchParamUnit.setValue(entry.getValue());
            searchParamUnit.setHitParam(HitParam.participle());
            searchParamUnit.setMultiQueryRule(MultiQueryRule.MUST);
            return searchParamUnit;
        }).collect(Collectors.toList());
    }

}
