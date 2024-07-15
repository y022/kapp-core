package com.kapp.kappcore.search.support.model.condition;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.model.exception.ValidateException;
import com.kapp.kappcore.search.support.option.DocOption;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/6/23 17:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateCondition extends AbstractCondition {

    /**
     * default bulk Update
     */
    private final boolean bulkUpdate = true;
    /**
     * doc updates only allow single index
     */
    private String actualIndex;
    /**
     * delete Id
     */
    private Set<String> delIds;
    /**
     * delete by query-sentence
     */
    private QueryBuilder queryBuilder;

    /**
     * update content
     */
    private List<Map<String, Object>> updateMap;

    public UpdateCondition(Set<String> index, DocOption option, List<Map<String, Object>> updateMap) {
        super(index, option);
        this.updateMap = updateMap;
        if (CollectionUtils.isNotEmpty(index)) {
            if (index.size() > 1) {
                throw new SearchException(ExCode.search_condition_error, "update is not support multiple index");
            }
            actualIndex = index().stream().findAny().get();
        }
    }

    public UpdateCondition() {

    }

    @Override
    public String read() {
        return "";
    }

    @Override
    public void validate() throws ValidateException {
        super.validate();
    }
}
