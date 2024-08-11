package com.kapp.kappcore.search.support.model.param;

import com.kapp.kappcore.search.support.model.SearchLimiter;
import com.kapp.kappcore.search.support.model.condition.ValCondition;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Author:Heping
 * Date: 2024/6/23 16:30
 */
@Data
public class SearchParam implements SearchMetrics {
    /**
     * 检索Id，每次都会生成新的，用于标识唯一
     */
    private String searchId;
    /**
     * 检索开始时间
     */
    private String startTime;
    /**
     * 检索结束时间
     */
    private String endTime;
    /**
     *
     */
    private boolean enableScroll;
    /**
     * scrollId，为空则表示当前不处于scroll模式
     */
    private String scrollId;
    /**
     * 索引
     */
    private String index;
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

    public String getSearchIndex() {
        return StringUtils.isBlank(index) ? condition.index() : index;
    }

    @Override
    public void searchId(String searchId) {
        this.searchId = searchId;
    }

    @Override
    public void startTime(String timestamp) {
        if (StringUtils.isBlank(startTime)) {
            this.startTime = timestamp;
        }
    }

    @Override
    public void endTime(String timestamp) {
        this.endTime = timestamp;

    }

    public boolean continueScroll() {
        return enableScroll && StringUtils.isNotBlank(scrollId);
    }
}
