package com.kapp.kappcore.web.api.biz.ext.wtt;

import com.kapp.kappcore.biz.ext.wtt.excel.WttExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/wtt/excel")
@RequiredArgsConstructor
public class ExcelApi {

    private final WttExcelService wttExcelService;
    @GetMapping("/export")
    void export(@RequestParam("tag") String tag, @RequestParam("no") String no, HttpServletResponse response) {
        wttExcelService.export(tag,no,response);
    }
}
