package com.kapp.kappcore.search.support.option;

import lombok.Getter;

/**
 * Author:Heping
 * Date: 2024/6/24 23:43
 * 响应字段，注意，无论选择任何方式，都会返回分数。如果不想返回分数，请直接关闭
 */
@Getter
public enum ViewType {
    SIMPLE("00", "Id"),
    TIP("01", "Id、标题"),
    ALL("02", "全部字段"),
    SET("03", "指定字段"),
    NONE("10", "不返回业务字段,分组统计中会用到");

    private final String code;
    private final String desc;

    ViewType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ViewType codeOf(String code) {
        for (ViewType value : ViewType.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
