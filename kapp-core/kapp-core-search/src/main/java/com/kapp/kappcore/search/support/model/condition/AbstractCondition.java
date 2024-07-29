package com.kapp.kappcore.search.support.model.condition;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.ValidateException;
import com.kapp.kappcore.search.support.option.DocOption;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/6/23 17:18
 */
@Data
@NoArgsConstructor
public abstract class AbstractCondition implements ValCondition {
    protected Set<String> indexes;
    protected DocOption option;

    public AbstractCondition(Set<String> indexes, DocOption option) {
        this.indexes = indexes;
        this.option = option;
    }

    @Override
    public Set<String> index() {
        return this.indexes;
    }

    @Override
    public DocOption option() {
        return getOption();
    }


    @Override
    public void validate() throws ValidateException {
        if (CollectionUtils.isEmpty(indexes)) {
            throw new ValidateException(ExCode.search_condition_error, "indexes is empty!");
        }
        if (option == null) {
            throw new ValidateException(ExCode.search_condition_error, "doc option is null!");
        }
    }

}
