package com.kapp.kappcore.biz.ext.wtt.excel.data;

import com.kapp.kappcore.wtt.ExcelDataTag;
import com.kapp.kappcore.wtt.ExcelModel;
import com.kapp.kappcore.wtt.ExportResult;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public interface ExcelDataSupport<T extends ExcelModel> {
    boolean support(ExcelDataTag tag);
    Map<String, T> get();

    void export(String no, HttpServletResponse response) throws IOException, IllegalAccessException;


}
