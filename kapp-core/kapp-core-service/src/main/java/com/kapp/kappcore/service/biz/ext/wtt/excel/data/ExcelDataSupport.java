package com.kapp.kappcore.service.biz.ext.wtt.excel.data;

import com.kapp.kappcore.model.wtt.ExcelDataTag;
import com.kapp.kappcore.model.wtt.ExcelModel;
import com.kapp.kappcore.model.wtt.ExportResult;

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
