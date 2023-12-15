package com.kapp.kappcore.biz.ext.wtt.excel.data;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.IoUtils;
import com.kapp.kappcore.annotaion.ExcelPosition;
import com.kapp.kappcore.biz.ext.wtt.excel.read.EasyExcelReadListener;
import com.kapp.kappcore.wtt.ControlValves;
import com.kapp.kappcore.wtt.ExcelDataTag;
import com.kapp.kappcore.wtt.ExportResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ControlValvesDataSupporter implements ExcelDataSupport<ControlValves> {

    private static final Map<String, ControlValves> MAP = new HashMap<>();

    static {
        File file = new File("C:\\Users\\y0225\\Desktop\\2D规格书-调节阀1.xls");
        EasyExcel.read(file, ControlValves.class, new EasyExcelReadListener(MAP)).sheet(1).doRead();
    }

    @Override
    public boolean support(ExcelDataTag tag) {
        return ExcelDataTag.CONTROL_VALVES.equals(tag);
    }

    @Override
    public Map<String, ControlValves> get() {
        return null;
    }

    @Override
    public void export(String no, HttpServletResponse response) throws IOException, IllegalAccessException {
        ControlValves controlValves = MAP.get(no);
         if (controlValves == null) {
            throw new RuntimeException("没有这条调节阀数据!");
        }

        FileInputStream templateFileInputStream = new FileInputStream("C:\\Users\\y0225\\Desktop\\调节阀导出模板.xlsx");

        Workbook workbook = WorkbookFactory.create(templateFileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

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

        response.setContentType("application/vnd.ms-excel;charset=uft-8");
        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("控制阀数据导出" +  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx","UTF-8"));

        workbook.write(response.getOutputStream());





        workbook.close();
        templateFileInputStream.close();
    }



}
