package com.kapp.kappcore.biz.ext.wtt.excel.write;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class ExcelHelper {

    public static Workbook mergeSheet(List<Workbook> workbooks, String mergeSheetName) {

        SXSSFWorkbook targetWorkbook = new SXSSFWorkbook();
        SXSSFSheet targetSheet = targetWorkbook.createSheet(mergeSheetName);
        for (int i = 0; i < workbooks.size(); i++) {
            Workbook sourceWorkbook = workbooks.get(i);
            Sheet sourceSheet = sourceWorkbook.getSheetAt(0);
            moveSourceSheetIntoTargetSheet(targetWorkbook, sourceSheet, targetSheet, i);
            try {
                sourceWorkbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        targetSheet.trackColumnForAutoSizing(8);
        targetSheet.autoSizeColumn(8);
        return targetWorkbook;
    }

    /**
     * 合并sheet页
     *
     * @param targetWorkBook
     * @param sourceSheet
     * @param targetSheet
     * @param sheetIndex
     */
    private static void moveSourceSheetIntoTargetSheet(Workbook targetWorkBook, Sheet sourceSheet, Sheet targetSheet, int sheetIndex) {

        if (sourceSheet == null) {
            return;
        }

        // 获取目标sheet最后一行的下一行
        int targetRowNums = sheetIndex * 55;
        // 移动源sheet页中的合并单元格区域 到目标sheet页中
        moveSourceSheetAllMergedRegionToTargetSheet(sourceSheet, targetSheet, targetRowNums);
        for (int i = 0; i <= 55; i++) {
            int targetRowNumNo = targetRowNums++;
            Row targetRow = targetSheet.createRow(targetRowNumNo);
            Row sourceRow = sourceSheet.getRow(i);
            // 复制行
            copySourceRowToTargetRow(targetWorkBook, sourceRow, targetRow);
        }
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
        CellType cellTypeEnum = sourceCell.getCellType();
        switch (cellTypeEnum) {
            case STRING:
                targetCell.setCellValue(sourceCell.getStringCellValue());
                break;
            case NUMERIC:
                targetCell.setCellValue(sourceCell.getNumericCellValue());
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
     * @param sourceSheet   源sheet
     * @param targetSheet   目标sheet
     * @param targetRowNums 目标sheet的最后一行（源合并单元格的位置，需要变化到目标单元格区域，需要提供一个位置角标）
     */
    private static void moveSourceSheetAllMergedRegionToTargetSheet(Sheet sourceSheet, Sheet targetSheet, int targetRowNums) {

        int numMergedRegions = sourceSheet.getNumMergedRegions();
        for (int i = 0; i < numMergedRegions; i++) {

            CellRangeAddress mergedRegion = sourceSheet.getMergedRegion(i);

            int firstRow = mergedRegion.getFirstRow();


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
