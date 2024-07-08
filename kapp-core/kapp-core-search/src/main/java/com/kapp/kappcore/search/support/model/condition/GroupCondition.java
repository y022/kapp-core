package com.kapp.kappcore.search.support.model.condition;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.model.exception.ValidateException;
import com.kapp.kappcore.search.support.model.param.GroupParamUnit;
import com.kapp.kappcore.search.support.option.DocOption;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/6/24 22:53
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupCondition extends AbstractSearchCondition {
    private int bucketCount = 100;
    private int subBucketCount = 10;
    private boolean bucketGroup = true;
    private List<GroupParamUnit> groupParamUnits;

    @Override
    public String read() {
        return "";
    }

    public GroupCondition() {
    }

    /**
     * 聚合使用精确匹配
     *
     * @param groupKeys 分组key
     * @return
     */
    public GroupCondition(Set<String> groupKeys) {
        GroupCondition condition = new GroupCondition(groupKeys);
        condition.setBucketGroup(true);
        condition.setOption(DocOption.GROUP);
    }

    @Override
    public void validate() throws ValidateException {
        if (bucketCount == 0 || bucketCount > searchField.getMaxGroupBucketCount()) {
            bucketCount = searchField.getMaxGroupBucketCount();
        }
        if (subBucketCount == 0 || subBucketCount > searchField.getMaxGroupBucketCount()) {
            subBucketCount = searchField.getMaxGroupSubBucketCount();
        }

    }

    @Override
    boolean searchAll() {
        return true;
    }

    @Override
    boolean hasMultiCondition() {
        return CollectionUtils.isNotEmpty(groupParamUnits);
    }

}
