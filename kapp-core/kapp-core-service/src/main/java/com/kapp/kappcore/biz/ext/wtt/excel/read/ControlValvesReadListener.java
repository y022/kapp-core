package com.kapp.kappcore.biz.ext.wtt.excel.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.kapp.kappcore.model.wtt.ControlValve;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ControlValvesReadListener implements ReadListener<ControlValve> {

    private Map<String, ControlValve> controlValvesList;

    public ControlValvesReadListener(Map<String, ControlValve> controlValvesList) {
        this.controlValvesList = controlValvesList;
    }

    @Override
    public void invoke(ControlValve controlValve, AnalysisContext analysisContext) {
        controlValvesList.putIfAbsent(controlValve.getControlValveNo(), controlValve);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("调节阀数据读取完毕!");
    }
}
