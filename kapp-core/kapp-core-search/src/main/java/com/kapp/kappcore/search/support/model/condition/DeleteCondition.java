package com.kapp.kappcore.search.support.model.condition;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.support.option.DocOption;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/6/24 14:16
 */
@EqualsAndHashCode(callSuper = true)
public class DeleteCondition extends AbstractCondition {
    private  Set<String> ids;

    public DeleteCondition() {
    }

    public DeleteCondition(Set<String> index, DocOption option, Set<String> ids) {
        super(index, option);
        this.ids = ids;
    }

    @Override
    public String read() {
        return StringUtils.join(ids, ",");
    }

    @Override
    public void checkAndCompensate() throws SearchException {
        if (ids == null || ids.isEmpty()) {
            throw new SearchException(ExCode.search_condition_error, "delete id can not be empty");
        }
    }

}
