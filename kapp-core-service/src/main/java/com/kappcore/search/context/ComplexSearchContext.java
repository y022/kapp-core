package com.kappcore.search.context;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class ComplexSearchContext extends AbsSearchContext {
    private boolean highlight;

    @Override
    public String tag() {
        return null;
    }

    @Override
    public boolean highlight() {
        return highlight;
    }

    @Override
    public boolean existSearchValue() {
        return super.existSearchValue() && (!StringUtils.hasText(source.getBody()) || !StringUtils.hasText(source.getTitle()));
    }
}
