package com.kapp.kappcore.search.support.model.condition;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.model.exception.ValidateException;
import com.kapp.kappcore.search.support.option.DocOption;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;

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
    private static final int MAX_SIZE = 1000;
    private List<Map<String, Object>> updateMap;
    private String actualIndex;

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
        if (CollectionUtils.isNotEmpty(updateMap)) {
            if (updateMap.size() > MAX_SIZE) {
                throw new ValidateException(ExCode.search_condition_error, "Exceeded the maximum batch operation limit!");
            }
        }
    }
}
