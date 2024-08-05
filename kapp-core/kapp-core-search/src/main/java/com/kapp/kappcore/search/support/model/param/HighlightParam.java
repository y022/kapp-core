package com.kapp.kappcore.search.support.model.param;

import lombok.Data;

/**
 * Author:Heping
 * Date: 2024/6/29 17:26
 */
@Data
public class HighlightParam {
    private final boolean highlight;
    private String preTag;
    private String postTag;

    public HighlightParam(final boolean highlight) {
        this.highlight = highlight;
    }

    public HighlightParam(boolean highlight, String preTag, String postTag) {
        this.highlight = highlight;
        this.preTag = preTag;
        this.postTag = postTag;
    }


    public static HighlightParam noneHighlight() {
        return new HighlightParam(false);
    }

}
