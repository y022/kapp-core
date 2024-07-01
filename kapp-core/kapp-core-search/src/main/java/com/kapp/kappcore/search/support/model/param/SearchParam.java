package com.kapp.kappcore.search.support.model.param;

import com.kapp.kappcore.search.support.model.SearchLimiter;
import com.kapp.kappcore.search.support.model.condition.ValCondition;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/6/23 16:30
 */
@Data
public class SearchParam {
    /**
     * 检索Id，每次都会生成新的，用于标识唯一
     */
    private String searchId;
    /**
     * 检索时间
     */
    private String searchTime;
    /**
     * 索引
     */
    private Set<String> indexes;
    /**
     * 检索限制，可为空，如果为空则表示不进行限制。
     */
    private SearchLimiter searchLimiter;
    /**
     * 检索条件
     */
    private ValCondition condition;
    /**
     * 相应是否返回检索参数
     */
    private boolean showParam = true;
    /**
     * 是否进行参数检查
     */
    private boolean paramCheck = true;

    public SearchParam() {

    }


    public Set<String> getSearchIndex() {
        return CollectionUtils.isEmpty(indexes) ? condition.index() : indexes;
    }


}
