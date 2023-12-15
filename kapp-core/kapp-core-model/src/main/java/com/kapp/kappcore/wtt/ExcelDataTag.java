package com.kapp.kappcore.wtt;

import lombok.Getter;

@Getter
public enum ExcelDataTag {
    CONTROL_VALVES("control_valves", "控制阀");;


    ExcelDataTag(String tag, String msg) {
        this.tag = tag;
        this.msg = msg;
    }

    private String tag;
    private String msg;
}
