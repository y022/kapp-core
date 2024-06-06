package com.kapp.kappcore.service.biz.ext.wtt.excel;

import com.kapp.kappcore.model.wtt.ExcelDataTag;
import com.kapp.kappcore.model.wtt.ExcelModel;
import com.kapp.kappcore.model.wtt.ExportResult;
import com.kapp.kappcore.service.biz.ext.wtt.excel.data.ExcelDataSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings(value = {"rawtypes", "unchecked"})
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
                    ExportResult exportResult = dataSupporter.export(no);
                    response.setContentType("application/vnd.ms-excel;charset=utf-8");
                    response.setHeader("Content-Disposition", "attachment;filename="
                            + URLEncoder.encode(exportResult.getFileName(), StandardCharsets.UTF_8));
                    IOUtil.copyCompletely(new ByteArrayInputStream(exportResult.getByteArrayOutputStream().toByteArray()), response.getOutputStream());
                } catch (RuntimeException runtimeException) {
                    throw runtimeException;
                } catch (Exception exception) {
                    log.error("导出失败！", exception);
                    throw new RuntimeException("导出失败!");
                }
                break;
            }
        }
    }

    public void batchExport(String tag, List<String> nos, HttpServletResponse response) {
        ExcelDataTag excelDataTag = ExcelDataTag.valueOf(tag);
        for (ExcelDataSupport<ExcelModel> dataSupporter : dataSupporters) {
            if (dataSupporter.support(excelDataTag)) {
                try {
                    ExportResult exportResult = dataSupporter.batchExport(nos);
                    response.setContentType("application/vnd.ms-excel;charset=utf-8");
                    response.setHeader("Content-Disposition", "attachment;filename="
                            + URLEncoder.encode(exportResult.getFileName(), StandardCharsets.UTF_8));
                    IOUtil.copyCompletely(new ByteArrayInputStream(exportResult.getByteArrayOutputStream().toByteArray()), response.getOutputStream());
                } catch (RuntimeException runtimeException) {
                    throw runtimeException;
                } catch (Exception exception) {
                    log.error("导出失败！", exception);
                    throw new RuntimeException("导出失败!");
                }
                break;
            }
        }
    }

    public void save(String tag) {
        ExcelDataTag excelDataTag = ExcelDataTag.valueOf(tag);
        for (ExcelDataSupport<?> dataSupporter : dataSupporters) {
            if (dataSupporter.support(excelDataTag)) {
                dataSupporter.save();
                break;
            }
        }
    }
}
