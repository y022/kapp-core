package com.kapp.kappcore.biz.ext;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.io.*;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class ExcelMergeTest {
    @Test
    public void mergeSheetTest() throws FileNotFoundException {
        String folderPath = "path/to/excel/files/";
        String mergedFilePath = "path/to/save/merged/excel/";
//        mergeExcelSheets(folderPath, mergedFilePath);
    }

    @Test
    public void mergeSheetTest2() {
        String filePath1 = "C:\\Users\\y0225\\Desktop\\1.xlsx";
        String filePath2 = "C:\\Users\\y0225\\Desktop\\2.xlsx";
        String mergedFilePath = "C:\\Users\\y0225\\Desktop\\mergedExcel.xlsx";
        try {
            // Read data from excel files
            Workbook workbook1 = WorkbookFactory.create(new FileInputStream(new File(filePath1)));
            Workbook workbook2 = WorkbookFactory.create(new FileInputStream(new File(filePath2)));

            // Get the first sheet from the workbooks
            Sheet sheet1 = workbook1.getSheetAt(0);
            Sheet sheet2 = workbook2.getSheetAt(0);

            // Create a new workbook and sheet for the merged data
            Workbook mergedWorkbook = WorkbookFactory.create(true);  // Set true for creating a new workbook
            Sheet mergedSheet = mergedWorkbook.createSheet("MergedData");


            // Copy the rows from sheet1 to mergedSheet
            // Copy the rows from sheet1 to mergedSheet


            this.copySheet(mergedSheet, sheet1, mergedWorkbook, workbook1, 0);
            this.copySheet(mergedSheet, sheet2, mergedWorkbook, workbook2, 60);


            // Save the merged workbook to a new file
            mergedWorkbook.write(new FileOutputStream(new File(mergedFilePath)));

            System.out.println("Excel files merged successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void copySheet(Sheet targetSheet, Sheet sourceSheet,
                          Workbook targetWork, Workbook sourceWork, int startRow) {
        if (targetSheet == null || sourceSheet == null || targetWork == null || sourceWork == null) {
            throw new IllegalArgumentException("调用PoiUtil.copySheet()方法时，targetSheet、sourceSheet、targetWork、sourceWork都不能为空，故抛出该异常！");
        }
        //复制源表中的行
        for (int i = sourceSheet.getFirstRowNum(); i <= sourceSheet.getLastRowNum(); i++) {
            Row sourceRow = sourceSheet.getRow(i);
            Row targetRow = targetSheet.createRow(i + startRow);  //创建新的row
            if (sourceRow != null) {
                copyRow(targetRow, sourceRow,
                        targetWork, sourceWork);
            }
        }
    }


    public void copyRow(Row targetRow, Row sourceRow,
                        Workbook targetWork, Workbook sourceWork) {
        if (targetRow == null || sourceRow == null || targetWork == null || sourceWork == null) {
            throw new IllegalArgumentException("调用PoiUtil.copyRow()方法时，targetRow、sourceRow、targetWork、sourceWork、targetPatriarch都不能为空，故抛出该异常！");
        }

        //设置行高
        targetRow.setHeight(sourceRow.getHeight());

        for (int i = sourceRow.getFirstCellNum(); i < sourceRow.getLastCellNum(); i++) {
            Cell sourceCell = sourceRow.getCell(i);
            Cell targetCell = null;

            if (sourceCell != null && sourceCell.getStringCellValue() != "") {
                if (targetCell == null) {
                    targetCell = targetRow.createCell(i);
                }
                //拷贝单元格，包括内容和样式
                copyCell(targetCell, sourceCell, targetWork, sourceWork);
            }
        }
    }


    public void copyCell(Cell targetCell, Cell sourceCell, Workbook targetWork, Workbook sourceWork) {
        if (targetCell == null || sourceCell == null || targetWork == null || sourceWork == null) {
            throw new IllegalArgumentException("调用PoiUtil.copyCell()方法时，targetCell、sourceCell、targetWork、sourceWork都不能为空，故抛出该异常！");
        }

        CellStyle targetCellStyle = targetWork.createCellStyle();
        targetCellStyle.cloneStyleFrom(sourceCell.getCellStyle());//拷贝样式
        targetCell.setCellStyle(targetCellStyle);
        targetCell.setCellValue(sourceCell.getStringCellValue());
    }


    @Test
    public void mergeSheetTest4() throws IOException {
        String filePath1 = "C:\\Users\\y0225\\Desktop\\1.xlsx";
        String filePath2 = "C:\\Users\\y0225\\Desktop\\2.xlsx";
        String mergedFilePath = "C:\\Users\\y0225\\Desktop\\mergedExcel.xlsx";
        File targetFile = new File(mergedFilePath);
        if (targetFile.exists()) {
            targetFile.delete();
        }
        targetFile.createNewFile();

        // Read data from excel files
        Workbook workbook1 = WorkbookFactory.create(new FileInputStream(new File(filePath1)));
        Workbook workbook2 = WorkbookFactory.create(new FileInputStream(new File(filePath2)));


        // Get the first sheet from the workbooks
        Sheet sheet1 = workbook1.getSheetAt(0);
        Sheet sheet2 = workbook2.getSheetAt(0);

        removeBlankRow(sheet1);
        removeBlankRow(sheet2);


        // Create a new workbook and sheet for the merged data
        Workbook mergedWorkbook = WorkbookFactory.create(true);  // Set true for creating a new workbook
        Sheet mergedSheet = mergedWorkbook.createSheet("MergedData");
        moveSourceSheetIntoTargetSheet(mergedWorkbook, sheet1, mergedSheet, 0, 0);
        moveSourceSheetIntoTargetSheet(mergedWorkbook, sheet2, mergedSheet, 0, 1);
        mergedWorkbook.write(new FileOutputStream(mergedFilePath));


    }

    private void removeBlankRow(Sheet sheet) {
        int i = sheet.getLastRowNum();
        Row tempRow;
        while (i > 0) {
            i--;
            tempRow = sheet.getRow(i);
            if (tempRow == null) {
                sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
            }

        }
    }

    /**
     * sheet页合并
     *
     * @param targetWorkBook    目标workbook，该对象主要用来创建单元格格式
     * @param sourceSheet       源sheet
     * @param targetSheet       目标sheet
     * @param removeTitleLength 源sheet页合并到目标sheet中需要去掉的表头长度，如果不去掉传递0
     */
    private static void moveSourceSheetIntoTargetSheet(Workbook targetWorkBook, Sheet sourceSheet, Sheet targetSheet, int removeTitleLength, int k) {

        if (sourceSheet == null) {
            return;
        }

        // 获取目标sheet最后一行的下一行
//        int targetRowNums = targetSheet.getLastRowNum();
//        int physicalNumberOfRows = targetSheet.getPhysicalNumberOfRows();
//        targetRowNums = physicalNumberOfRows == 0 ? 0 : targetRowNums + 1;
        int targetRowNums = k * 58;

        // 移动 源sheet页中的 合并单元格区域 到目标sheet页中
        moveSourceSheetAllMergedRegionToTargetSheet(sourceSheet, targetSheet, targetRowNums, removeTitleLength);

        int sourceRowNums = sourceSheet.getLastRowNum();
        for (int i = removeTitleLength; i <= sourceRowNums; i++) {

            Row targetRow = targetSheet.createRow(targetRowNums++);
            Row sourceRow = sourceSheet.getRow(i);

            // 复制行
            copySourceRowToTargetRow(targetWorkBook, sourceRow, targetRow);
        }

    }

    private static int getLawRowNum(Sheet sheet) {
        int rowCount = sheet.getLastRowNum();
        int rowNums = 0;
//这里是去除表格里的空行，防止行数干扰
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            boolean rowEmpty = isRowNotEmpty(row);
            if (null != row && rowEmpty && row.getFirstCellNum() > -1) {
                rowNums++;
            }
        }
        return rowNums;
    }

    public static boolean isRowNotEmpty(Row row) {
        if (!StringUtils.isEmpty(row)) {
            for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
                Cell cell = row.getCell(c);
                if (cell != null && cell.getCellType() != BLANK) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将源行复制到目标行
     *
     * @param targetWorkBook 目标workbook，主要用来创建单元格样式
     * @param sourceRow      源行
     * @param targetRow      目标行
     */
    private static void copySourceRowToTargetRow(Workbook targetWorkBook, Row sourceRow, Row targetRow) {

        if (sourceRow == null) {
            return;
        }

        // 行高
        targetRow.setHeight(sourceRow.getHeight());

        int sourceCellNums = sourceRow.getLastCellNum();
        for (int i = 0; i < sourceCellNums; i++) {

            Cell targetCell = targetRow.createCell(i);
            Cell sourceCell = sourceRow.getCell(i);

            // 复制单元格
            copySourceCellToTargetCell(targetWorkBook, targetCell, sourceCell);
        }

    }

    /**
     * 移动单元格
     *
     * @param targetWorkBook 目标workbook，用来在本方法中创建单元格样式
     * @param targetCell     目标单元格
     * @param sourceCell     源单元格
     */
    private static void copySourceCellToTargetCell(Workbook targetWorkBook, Cell targetCell, Cell sourceCell) {

        if (sourceCell == null) {
            return;
        }

        // 将源单元格的格式 赋值到 目标单元格中
        CellStyle sourceCellStyle = sourceCell.getCellStyle();
        /*
            此处由于是新建了workbook对象，只能新建 CellStyle对象，然后clone，再赋值；
            直接赋值 源CellStyle对象 会报不是同源异常
        */
        CellStyle targetCellStyle = targetWorkBook.createCellStyle();
        targetCellStyle.cloneStyleFrom(sourceCellStyle);
        targetCell.setCellStyle(targetCellStyle);

        CellType cellTypeEnum = sourceCell.getCellTypeEnum();
        switch (cellTypeEnum) {
            case STRING:
                targetCell.setCellValue(sourceCell.getStringCellValue());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(sourceCell)) {
                    // 日期格式的值
                    targetCell.setCellValue(sourceCell.getDateCellValue());
                } else {
                    targetCell.setCellValue(sourceCell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                targetCell.setCellValue(sourceCell.getBooleanCellValue());
                break;
            case FORMULA:
                // ***为公式的情况下获取的是单元格的数值
                targetCell.setCellValue(sourceCell.getNumericCellValue());
                break;
            case BLANK:
                break;
            case ERROR:
                targetCell.setCellValue(sourceCell.getErrorCellValue());
                break;
            case _NONE:
                break;
            default:

        }
    }

    /**
     * 合并sheet页中，处理源sheet页中可能存在的 合并单元格部分；
     * 当源sheet页在合并单元格的时候可能去掉表头，所以也需去掉表头的合并单元格部分
     *
     * @param sourceSheet       源sheet
     * @param targetSheet       目标sheet
     * @param targetRowNums     目标sheet的最后一行（源合并单元格的位置，需要变化到目标单元格区域，需要提供一个位置角标）
     * @param removeTitleLength 需要移除的表头长度
     */
    private static void moveSourceSheetAllMergedRegionToTargetSheet(Sheet sourceSheet, Sheet targetSheet, int targetRowNums, int removeTitleLength) {

        int numMergedRegions = sourceSheet.getNumMergedRegions();
        for (int i = 0; i < numMergedRegions; i++) {

            CellRangeAddress mergedRegion = sourceSheet.getMergedRegion(i);

            int firstRow = mergedRegion.getFirstRow();

            // 去掉表头的 单元格合并
            if (firstRow < removeTitleLength) {
                continue;
            }

            int lastRow = mergedRegion.getLastRow();
            int firstColumn = mergedRegion.getFirstColumn();
            int lastColumn = mergedRegion.getLastColumn();

            // 合并单元格的行需要跟随当前单元格的行数下移
            firstRow = firstRow + targetRowNums;
            lastRow = lastRow + targetRowNums;

            CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow, firstColumn, lastColumn);
            targetSheet.addMergedRegion(cellRangeAddress);
        }

    }
}



