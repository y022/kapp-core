package com.kapp.kappcore.search.support.model.param;

import com.kapp.kappcore.search.support.option.MultiQueryRule;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/6/25 10:16
 * 最小检索参数单元
 */
@Data
@NoArgsConstructor
public class SearchParamUnit {
    private String key;
    private Object value;
    private Set<Object> values;
    private RangeParam rangeParam;
    private MultiQueryRule multiQueryRule;
    private HitParam hitParam;
    private String analyzerName;

    public SearchParamUnit(String key, Object value, Set<Object> values, RangeParam rangeParam, MultiQueryRule multiQueryRule, HitParam hitParam) {
        this.key = key;
        this.value = value;
        this.values = values;
        this.rangeParam = rangeParam;
        this.multiQueryRule = multiQueryRule;
        this.hitParam = hitParam;
    }

}
