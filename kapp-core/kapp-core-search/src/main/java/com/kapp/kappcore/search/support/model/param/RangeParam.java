package com.kapp.kappcore.search.support.model.param;

import lombok.Data;

/**
 * Author:Heping
 * Date: 2024/6/28 18:54
 */
@Data
public class RangeParam {
    private String lte;
    private String gte;
    private boolean includeLow;
    private boolean includeHigh;

}
