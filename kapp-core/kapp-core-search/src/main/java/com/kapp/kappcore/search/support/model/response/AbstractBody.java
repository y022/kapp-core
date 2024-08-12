package com.kapp.kappcore.search.support.model.response;

import lombok.Data;

/**
 * Author:Heping
 * Date: 2024/7/26 17:59
 */
@Data
public abstract class AbstractBody implements Body {
    protected long took = 0;
    protected long total = 0;

    @Override
    public long took() {
        return 0;
    }

    @Override
    public long total() {
        return 0;
    }

}
