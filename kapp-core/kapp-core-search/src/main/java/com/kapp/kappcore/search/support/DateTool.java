package com.kapp.kappcore.search.support;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.BizException;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTool {
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");

    public static String now() {
        return _now().format(DF);
    }


    public static boolean expire(String timeTxt, Duration duration) {
        if (StringUtils.isBlank(timeTxt) || duration == null) {
            throw new BizException(ExCode.DATE_BLANK, "timeTxt or duration is not blank");
        }
        //compute expire time
        LocalDateTime expireTimePoint = LocalDateTime.parse(timeTxt, DF).plus(duration);
        return _now().isAfter(expireTimePoint);
    }

    private static LocalDateTime _now() {
        return LocalDateTime.now();
    }
}