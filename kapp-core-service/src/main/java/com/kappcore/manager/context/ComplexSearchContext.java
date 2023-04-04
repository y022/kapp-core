package com.kappcore.manager.context;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class ComplexSearchContext extends AbsSearchContext {


    @Override
    public String tag() {
        return null;
    }

    @Override
    public boolean highlight(boolean highlight) {
        return false;
    }

    @Override
    public boolean existSearchValue() {
        return super.existSearchValue() && (!StringUtils.hasText(source.getBody()) || !StringUtils.hasText(source.getTitle()));
    }
}
