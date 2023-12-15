package com.kapp.kappcore.biz.ext.wtt.excel;

import com.alibaba.excel.util.IoUtils;
import com.kapp.kappcore.biz.ext.wtt.excel.data.ExcelDataSupport;
import com.kapp.kappcore.wtt.ExcelDataTag;
import com.kapp.kappcore.wtt.ExcelModel;
import com.kapp.kappcore.wtt.ExportResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WttExcelService {

    private final List<ExcelDataSupport> dataSupporters = new ArrayList<>();
    private final ApplicationContext applicationContext;

    @PostConstruct
    private void init() {
        dataSupporters.addAll(applicationContext.getBeansOfType(ExcelDataSupport.class).values());
    }

    public void export(String tag, String no, HttpServletResponse response) {
        ExcelDataTag excelDataTag = ExcelDataTag.valueOf(tag);
        for (ExcelDataSupport<ExcelModel> dataSupporter : dataSupporters) {
            if (dataSupporter.support(excelDataTag)) {
                try {
                     dataSupporter.export(no,response);
                } catch (RuntimeException runtimeException) {
                    throw runtimeException;
                } catch (Exception exception) {
                    log.error("导出失败！", exception);
                }
            }
            break;
        }
    }


}
