package com.kapp.kappcore.biz.ext.wtt.excel.data;

import com.kapp.kappcore.wtt.ExcelDataTag;
import com.kapp.kappcore.wtt.ExcelModel;
import com.kapp.kappcore.wtt.ExportResult;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

public interface ExcelDataSupport<T extends ExcelModel> {
    boolean support(ExcelDataTag tag);

    Map<String, T> get();

    void save();

    void save(T t);

    ExportResult export(String no);

    ExportResult batchExport(List<String> no);

    void importData(List<T> t);

    void importData(ByteArrayInputStream inputStream);

}
