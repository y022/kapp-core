package com.kapp.kappcore.wtt.query;

import com.kapp.kappcore.wtt.ControlValve;
import lombok.Data;

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
