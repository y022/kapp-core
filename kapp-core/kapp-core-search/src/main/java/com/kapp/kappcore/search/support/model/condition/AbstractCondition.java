package com.kapp.kappcore.search.support.model.condition;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.ValidateException;
import com.kapp.kappcore.search.support.option.DocOption;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * Author:Heping
 * Date: 2024/6/23 17:18
 */
@Data
@NoArgsConstructor
public abstract class AbstractCondition implements ValCondition {
    protected String index;
    protected DocOption option;

    public AbstractCondition(String index, DocOption option) {
        this.index = index;
        this.option = option;
    }

    @Override
    public String index() {
        return this.index;
    }

    @Override
    public String index(String index) {
        if (StringUtils.isNotBlank(index)) {
            this.index = index;
        }
        return index();
    }

    @Override
    public DocOption option() {
        return getOption();
    }


    @Override
    public void validate() throws ValidateException {
        if (StringUtils.isBlank(index)) {
            throw new ValidateException(ExCode.search_condition_error, "indexes is blank!");
        }
        if (option == null) {
            throw new ValidateException(ExCode.search_condition_error, "doc option not exists!");
        }
    }

}
