package com.kapp.kappcore.biz.ext.wtt.excel;

import com.kapp.kappcore.biz.ext.wtt.excel.data.ExcelDataSupport;
import com.kapp.kappcore.wtt.ExcelDataTag;
import com.kapp.kappcore.wtt.ExcelModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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

    @SuppressWarnings(value = "unchecked")
    public void export(String tag, String no, HttpServletResponse response) {
        log.info("开始导出调节阀数据,位号:" + no);
        ExcelDataTag excelDataTag = ExcelDataTag.valueOf(tag);
        for (ExcelDataSupport<ExcelModel> dataSupporter : dataSupporters) {
            if (dataSupporter.support(excelDataTag)) {
                try {
                    dataSupporter.export(no, response);
                } catch (RuntimeException runtimeException) {
                    throw runtimeException;
                } catch (Exception exception) {
                    log.error("导出失败！", exception);
                    throw new RuntimeException("导出失败!");
                }
            }
            break;
        }
        log.info("调节阀数据导出完成,位号:" + no);
    }
}
