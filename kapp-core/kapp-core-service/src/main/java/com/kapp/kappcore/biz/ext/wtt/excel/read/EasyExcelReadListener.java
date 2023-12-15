package com.kapp.kappcore.biz.ext.wtt.excel.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.kapp.kappcore.wtt.ControlValves;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class EasyExcelReadListener implements ReadListener<ControlValves> {

    private Map<String, ControlValves> controlValvesList;

    public EasyExcelReadListener(Map<String, ControlValves> controlValvesList) {
        this.controlValvesList = controlValvesList;
    }

    @Override
    public void invoke(ControlValves controlValves, AnalysisContext analysisContext) {
        controlValvesList.putIfAbsent(controlValves.getControlValvesNo(), controlValves);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("调节阀数据读取完毕!");
    }
}
