package com.kapp.kappcore.biz.ext.wtt.excel.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapp.kappcore.annotaion.CellPosition;
import com.kapp.kappcore.biz.ext.wtt.excel.write.ExcelHelper;
import com.kapp.kappcore.domain.repository.ControlValveRepository;
import com.kapp.kappcore.wtt.ControlValve;
import com.kapp.kappcore.wtt.ExcelDataTag;
import com.kapp.kappcore.wtt.ExportResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ControlValvesDataSupporter implements ExcelDataSupport<ControlValve> {

    private static final Map<String, ControlValve> MAP = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ControlValveRepository controlValveRepository;
    private static final String CONTROL_VALVE_TEMPLATE_LOCATION = "/template/调节阀导出模板.xlsx";

    @Override
    public boolean support(ExcelDataTag tag) {
        return ExcelDataTag.CONTROL_VALVES.equals(tag);
    }

    @Override
    public Map<String, ControlValve> get() {
        return MAP;
    }

    @Override
    public void save() {
        controlValveRepository.saveAll(MAP.values());
        log.info("共保存数据:" + MAP.size());
    }

    @Override
    public void save(ControlValve controlValve) {
        controlValveRepository.save(controlValve);
    }


    @Override
    public ExportResult export(String no) {
        log.info("开始导出调节阀数据,位号:" + no);
        ControlValve controlValve = controlValveRepository.findByControlValveNo(no);
        if (controlValve == null) {
            throw new RuntimeException("没有这条调节阀数据!");
        }
        ExportResult exportResult = new ExportResult();
        Workbook workbook = getWorkbook(controlValve, no);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException("工作簿写出失败!");
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {

            }
        }
        exportResult.setByteArrayOutputStream(byteArrayOutputStream);
        exportResult.setFileName("控制阀数据导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx");
        log.info("调节阀数据导出完成,位号:" + no);
        return exportResult;
    }

    @Override
    public ExportResult batchExport(List<String> controlValveNos) {
        log.info("开始批量导出调节阀数据,位号:" + controlValveNos);
        List<Workbook> workbooks = new ArrayList<>();

        CountDownLatch countDownLatch = new CountDownLatch(controlValveNos.size());

        for (String controlValveNo : controlValveNos) {

            CompletableFuture.runAsync(() -> {
                try {
                    ControlValve controlValve = controlValveRepository.findByControlValveNo(controlValveNo);
                    workbooks.add(getWorkbook(controlValve, controlValveNo));
                } finally {
                    countDownLatch.countDown();
                }


            });

        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Workbook mergedWorkbook = ExcelHelper.mergeSheet(workbooks, "控制阀");
        ExportResult exportResult = new ExportResult();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            mergedWorkbook.write(byteArrayOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            mergedWorkbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        exportResult.setByteArrayOutputStream(byteArrayOutputStream);
        exportResult.setFileName("控制阀数据导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx");
        return exportResult;
    }

    @Override
    public void importData(List<ControlValve> t) {
        controlValveRepository.saveAll(t);
        log.info("导入数据成功,位号:" + t.stream().map(ControlValve::getControlValveNo).collect(Collectors.toList()));
    }

    @Override
    public void importData(ByteArrayInputStream inputStream) {
        ControlValve controlValve;
        byte[] bytes = inputStream.readAllBytes();
        try {
            controlValve = objectMapper.readValue(bytes, ControlValve.class);
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException("读取文件数据失败!");
        }finally {
            try {
                IOUtils.close(inputStream);
            } catch (IOException e) {
            }
        }
        controlValveRepository.save(controlValve);
        log.info("导入数据成功,位号:" + controlValve.getControlValveNo());
    }

    private Workbook getWorkbook(ControlValve controlValve, String sheetName) {
        ClassPathResource classPathResource = new ClassPathResource(CONTROL_VALVE_TEMPLATE_LOCATION);
        try (InputStream templateFileInputStream = classPathResource.getInputStream();) {
            Workbook workbook = WorkbookFactory.create(templateFileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            workbook.setSheetName(0, sheetName);
            Class<? extends ControlValve> clz = controlValve.getClass();
            for (Field declaredField : clz.getDeclaredFields()) {
                declaredField.setAccessible(true);
                CellPosition annotation = declaredField.getAnnotation(CellPosition.class);
                if (annotation == null) {
                    continue;
                }
                String cellPosition = annotation.cellRef();
                CellReference cellReference = new CellReference(cellPosition);
                Cell cell = sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol());
                Object value = declaredField.get(controlValve);
                cell.setCellValue(value == null ? "" : value.toString());
            }
            return workbook;
        } catch (IOException ioException) {
            throw new RuntimeException("io异常");
        } catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException("illegalAccessException!");
        }
    }
}
