package com.kapp.kappcore.model.wtt.query;

import lombok.Data;

import com.kapp.kappcore.model.wtt.ControlValve;

import java.util.List;

@Data
public class ControlValveQueryResp {
    private long total;
    private List<ControlValve> data;

    public static ControlValveQueryResp of(List<ControlValve> data, long total) {
        ControlValveQueryResp resp = new ControlValveQueryResp();
        resp.setTotal(total);
        resp.setData(data);
        return resp;
    }
}
