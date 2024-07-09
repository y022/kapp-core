package com.kapp.kappcore.search.support.model.condition;

import com.kapp.kappcore.model.exception.ValidateException;
import com.kapp.kappcore.search.support.option.DocOption;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private List<Map<String, Object>> updateMap;

    public UpdateCondition(Set<String> index, DocOption option, List<Map<String, Object>> updateMap) {
        super(index, option);
        this.updateMap = updateMap;
    }

    public UpdateCondition() {
    }

    @Override
    public String read() {
        return "";
    }

    @Override
    public void validate() throws ValidateException {

    }
}
