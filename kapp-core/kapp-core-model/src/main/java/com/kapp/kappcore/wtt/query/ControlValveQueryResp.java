package com.kapp.kappcore.wtt.query;

import com.kapp.kappcore.wtt.ControlValve;
import lombok.Data;

import java.util.List;

@Data
public class ControlValveQueryResp {
    private long total;
    private List<ControlValve> data;
}
