package com.kapp.kappcore.support.log;

import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.event.LoggingEvent;

/**
 * Author:Heping
 * Date: 2024/6/16 0:24
 */
public class LogControl extends Filter<LoggingEvent> {


    @Override
    public FilterReply decide(LoggingEvent loggingEvent) {
//        if (StringUtils.isNotBlank(MDC.get("FLAGA"))) {
//            return FilterReply.DENY;
//        }
        return FilterReply.ACCEPT;
    }
}
