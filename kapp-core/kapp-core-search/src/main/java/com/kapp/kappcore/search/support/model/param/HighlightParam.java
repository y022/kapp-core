package com.kapp.kappcore.search.support.model.param;

import lombok.Data;

/**
 * Author:Heping
 * Date: 2024/6/29 17:26
 */
@Data
public class HighlightParam {
    private boolean highlight;
    private String preTag;
    private String postTag;

    public static HighlightParam instance() {
        return new HighlightParam();
    }
}
