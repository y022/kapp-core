package com.kapp.kappcore.search.context;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

@Data
@EqualsAndHashCode(callSuper = true)
public class ComplexSearchContext extends AbsSearchContext {
    private boolean highlight;

    @Override
    public String tag() {
        return super.source.getTag();
    }

    @Override
    public boolean highlight() {
        return highlight;
    }

    @Override
    public boolean noSearchValue() {
        return super.noSearchValue() || (!StringUtils.hasText(source.getBody()) && !StringUtils.hasText(source.getTitle()));
    }
}
