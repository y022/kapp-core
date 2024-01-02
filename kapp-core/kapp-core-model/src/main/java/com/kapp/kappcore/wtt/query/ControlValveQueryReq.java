package com.kapp.kappcore.wtt.query;

import lombok.Data;

@Data
public class ControlValveQueryReq {
    private String no;
    private String purpose;
    private int index = 1;
    private int size = 10;
}
