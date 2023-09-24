package com.kapp.kappcore.web.update;

import com.kapp.kappcore.constant.ApiName;
import com.kapp.kappcore.web.vo.update.SaveRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiName.UPDATE_API)
@RequiredArgsConstructor
public class UpdateApi {

    @PostMapping("/save")
    void save(@RequestBody SaveRequestVo saveRequestVo) {

    }
}
