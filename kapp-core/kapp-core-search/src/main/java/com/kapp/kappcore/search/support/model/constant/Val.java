package com.kapp.kappcore.search.support.model.constant;

import org.elasticsearch.common.unit.TimeValue;

/**
 * Author:Heping
 * Date: 2024/6/26 18:36
 */
public class Val {
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String _AGG = "_agg";


    public static final TimeValue SCROLL_KEEP_ALIVE = TimeValue.timeValueMinutes(1);

}
