package com.kapp.kappcore.web.api.biz.share.common.message;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/share/common/message")
@RequiredArgsConstructor
public class MessageApi {

    @GetMapping("/queryMessageCount")
    Long queryMessageCount(@RequestParam("userId") String userId) {
        return 100L;
    }
}
