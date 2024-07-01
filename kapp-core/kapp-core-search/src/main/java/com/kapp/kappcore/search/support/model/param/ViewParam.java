package com.kapp.kappcore.search.support.model.param;

import com.kapp.kappcore.search.support.option.ViewType;
import lombok.Data;

import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/6/30 18:02
 */
@Data
public class ViewParam {
    private ViewType viewType;
    private Set<String> viewFields;

    public static ViewParam defaultView() {
        ViewParam viewParam = new ViewParam();
        viewParam.setViewFields(Set.of());
        viewParam.setViewType(ViewType.ALL);
        return viewParam;
    }
}
