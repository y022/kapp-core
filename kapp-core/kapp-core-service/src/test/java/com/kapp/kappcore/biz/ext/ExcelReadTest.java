package com.kapp.kappcore.biz.ext;


import com.alibaba.excel.EasyExcel;
import com.kapp.kappcore.annotaion.ExcelPosition;
import com.kapp.kappcore.biz.ext.wtt.excel.read.ControlValvesReadListener;
import com.kapp.kappcore.wtt.ControlValves;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ExcelReadTest {
    @Test
    public void read() {
        File file = new File("C:\\Users\\y0225\\Desktop\\2D规格书-调节阀1.xls");
        HashMap<String, ControlValves> excelResult = new HashMap<>();
        EasyExcel.read(file, ControlValves.class, new ControlValvesReadListener(excelResult)).sheet(1).doRead();
    }

    @Test
    public void write() throws IOException {
        //先读数据文件
        File file = new File("C:\\Users\\y0225\\Desktop\\2D规格书-调节阀1.xls");
        HashMap<String, ControlValves> excelResult = new HashMap<>();
        EasyExcel.read(file, ControlValves.class, new ControlValvesReadListener(excelResult)).sheet(1).doRead();


        //读模板文件
        FileInputStream templateFileInputStream = new FileInputStream("C:\\Users\\y0225\\Desktop\\调节阀导出模板.xlsx");


        //创建导出结果文件
        File targetFile = new File("C:\\Users\\y0225\\Desktop\\调节阀导出测试结果.xlsx");
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        } else {
            targetFile.delete();
            targetFile.createNewFile();
        }
        ;

        ControlValves controlValves = excelResult.get("1168-FV-10201");

        Workbook workbook = WorkbookFactory.create(templateFileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        CellReference d8 = new CellReference("D8");
        Cell cell1 = sheet.getRow(d8.getRow()).getCell(d8.getCol());
        cell1.setCellValue(controlValves.getControlValvesNo());

        CellReference d9 = new CellReference("D9");
        Cell cellD9 = sheet.getRow(d9.getRow()).getCell(d9.getCol());
        cellD9.setCellValue(controlValves.getSize());


        FileOutputStream targetFileOutputStream = new FileOutputStream(targetFile);
        workbook.write(targetFileOutputStream);
        targetFileOutputStream.close();

    }

    @Test
    public void write_01() throws IOException, IllegalAccessException {
        //先读数据文件
        File file = new File("C:\\Users\\y0225\\Desktop\\2D规格书-调节阀1.xls");
        HashMap<String, ControlValves> excelResult = new HashMap<>();
        EasyExcel.read(file, ControlValves.class, new ControlValvesReadListener(excelResult)).sheet(1).doRead();


        //读模板文件
        FileInputStream templateFileInputStream = new FileInputStream("C:\\Users\\y0225\\Desktop\\调节阀导出模板.xlsx");
         //创建导出结果文件
        File targetFile = new File("C:\\Users\\y0225\\Desktop" + File.separator + "调节阀导出结果" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx");
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        } else {
            targetFile.delete();
            targetFile.createNewFile();
        }

        ControlValves controlValves = excelResult.get("1168-FV-10201");

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

        FileOutputStream targetFileOutputStream = new FileOutputStream(targetFile);
        workbook.write(targetFileOutputStream);
        targetFileOutputStream.close();

    }
}
