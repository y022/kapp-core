package com.kapp.kappcore.web.api.biz.share;

import com.kapp.kappcore.web.vo.share.order.OrderQueryRequestVo;
import com.kapp.kappcore.web.vo.share.order.OrderQueryResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/share/order")
@RequiredArgsConstructor
public class OrderApi {

    @PostMapping("/query")
    OrderQueryResponseVo queryAll(@RequestBody OrderQueryRequestVo request) {


        return null;
    }
}
