package com.kapp.kappcore.biz.ext.wtt.excel.data;

import com.alibaba.excel.EasyExcel;
import com.kapp.kappcore.annotaion.ExcelPosition;
import com.kapp.kappcore.biz.ext.wtt.excel.read.ControlValvesReadListener;
import com.kapp.kappcore.wtt.ControlValves;
import com.kapp.kappcore.wtt.ExcelDataTag;
import com.kapp.kappcore.wtt.ExportResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ControlValvesDataSupporter implements ExcelDataSupport<ControlValves> {

    private static final Map<String, ControlValves> MAP = new HashMap<>();

    static {
        File file = new File("/home/jar/controlValvesSource.xls");
        EasyExcel.read(file, ControlValves.class, new ControlValvesReadListener(MAP)).sheet(1).doRead();
        log.info("加载控制阀数据条数:" + MAP.size());
    }

    @Override
    public boolean support(ExcelDataTag tag) {
        return ExcelDataTag.CONTROL_VALVES.equals(tag);
    }

    @Override
    public Map<String, ControlValves> get() {
        return MAP;
    }

    @Override
    public void export(String no, HttpServletResponse response) throws IOException, IllegalAccessException {
        ControlValves controlValves = MAP.get(no);
        if (controlValves == null) {
            throw new RuntimeException("没有这条调节阀数据!");
        }

        FileInputStream templateFileInputStream = new FileInputStream("/home/jar/controlValvesTemplate.xlsx");

        Workbook workbook = WorkbookFactory.create(templateFileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        workbook.setSheetName(0,no);
        Class<? extends ControlValves> clz = controlValves.getClass();

        for (Field declaredField : clz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            ExcelPosition annotation = declaredField.getAnnotation(ExcelPosition.class);
            if (annotation == null) {
                continue;
            }
            String cellPosition = annotation.cellRef();
            CellReference cellReference = new CellReference(cellPosition);
            Cell cell = sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol());
            Object value = declaredField.get(controlValves);
            cell.setCellValue(value == null ? "" : value.toString());
        }

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode("控制阀数据导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx", "UTF-8"));

        workbook.write(response.getOutputStream());

        workbook.close();

        templateFileInputStream.close();
    }

    @Override
    public ExportResult export(String no) {
        ControlValves controlValves = MAP.get(no);
        if (controlValves == null) {
            throw new RuntimeException("没有这条调节阀数据!");
        }

        return null;
    }


}
