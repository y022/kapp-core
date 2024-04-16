package com.kapp.kappcore.web.api.biz.ext.wtt;

import com.kapp.kappcore.biz.ext.wtt.excel.QueryService;
import com.kapp.kappcore.biz.ext.wtt.excel.WttExcelService;
import com.kapp.kappcore.model.wtt.query.ControlValveQueryReq;
import com.kapp.kappcore.model.wtt.query.ControlValveQueryResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/wtt/excel")
@RequiredArgsConstructor
public class ExcelApi {

    private final WttExcelService wttExcelService;
    private final QueryService queryService;

    @GetMapping("/export")
    void export(@RequestParam("tag") String tag, @RequestParam("no") String no, HttpServletResponse response) {
        wttExcelService.export(tag, no, response);
    }

    @PostMapping("/batchExport")
    void batchExport(@RequestParam("tag") String tag, @RequestBody List<String> no, HttpServletResponse response) {
        wttExcelService.batchExport(tag, no, response);
    }

    @GetMapping("/save")
    void export(@RequestParam("tag") String tag) {
        wttExcelService.save(tag);
    }

    @PostMapping("query")
    ControlValveQueryResp query(@RequestBody ControlValveQueryReq query) {
        return queryService.query(query);
    }
}
