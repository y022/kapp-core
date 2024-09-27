package com.kapp.kappcore.web.api.biz.share;

import com.kapp.kappcore.model.dto.share.WSOrderAdd;
import com.kapp.kappcore.model.dto.share.WSOrderQueryDTO;
import com.kapp.kappcore.model.dto.share.WSOrderQueryResult;
import com.kapp.kappcore.service.biz.share.curd.WSOrderService;
import com.kapp.kappcore.support.lock.KappLock;
import com.kapp.kappcore.web.vo.share.order.order.OrderAddRequestVo;
import com.kapp.kappcore.web.vo.share.order.order.OrderQueryRequestVo;
import com.kapp.kappcore.web.vo.share.order.order.OrderQueryResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/share/order")
@RequiredArgsConstructor
public class OrderApi {
    private final WSOrderService wsOrderService;
    private final MapperFacade mapperFacade;
    private final KappLock kappLock;

    @PostMapping("/query")
    OrderQueryResponseVo queryAll(@RequestBody OrderQueryRequestVo request) {
        log.info("xxxxxxxxx");
        WSOrderQueryResult batch = wsOrderService.batch(mapperFacade.map(request, WSOrderQueryDTO.class));
        return mapperFacade.map(batch, OrderQueryResponseVo.class);
    }

    @PostMapping("/add")
    void add(@RequestBody OrderAddRequestVo request) {
        wsOrderService.add(mapperFacade.map(request, WSOrderAdd.class));
    }

    @PostMapping("/tryLock")
    void tryLock(@RequestParam("key") String key) {
        kappLock.lock(key);
    }
}
