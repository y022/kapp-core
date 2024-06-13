package com.kapp.kappcore.service.biz.share.curd;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.dto.share.PriceComputeDTO;
import com.kapp.kappcore.model.dto.share.WSOrderAdd;
import com.kapp.kappcore.model.dto.share.WSOrderQueryDTO;
import com.kapp.kappcore.model.dto.share.WSOrderQueryResult;
import com.kapp.kappcore.model.dto.share.message.MessageType;
import com.kapp.kappcore.model.dto.share.message.impl.TimeKappMessage;
import com.kapp.kappcore.model.entity.share.WS;
import com.kapp.kappcore.model.entity.share.WSOrder;
import com.kapp.kappcore.model.exception.BizException;
import com.kapp.kappcore.service.biz.share.message.WSOrderMessageCenter;
import com.kapp.kappcore.service.domain.repository.share.OrderTool;
import com.kapp.kappcore.service.domain.repository.share.order.WSOrderRepository;
import com.kapp.kappcore.service.domain.repository.share.ws.WSOrderQuerySupporter;
import com.kapp.kappcore.service.domain.repository.share.ws.WSRepository;
import com.kapp.kappcore.support.lock.KappLock;
import com.kapp.kappcore.support.tool.date.DateTool;
import com.kapp.kappcore.support.tool.id.IdTool;
import com.kapp.kappcore.support.transaction.TransactionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class WSOrderService {
    private final WSOrderRepository wsOrderRepository;
    private final WSRepository wsRepository;
    private final KappLock kappLock;
    private final WSOrderMessageCenter wsOrderMessageCenter;
    private final WSOrderQuerySupporter wsOrderQuerySupporter;

    public WSOrderQueryResult batch(WSOrderQueryDTO wsOrderQueryDTO) {
        return wsOrderQuerySupporter.batchQueryOrder(wsOrderQueryDTO);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void add(WSOrderAdd wsOrderAdd) {
        String wsId = wsOrderAdd.getWsId();
        WS ws = wsRepository.findById(wsId).orElseThrow(() -> new NullPointerException("wsId:" + wsId + " not found!"));

        if (ws.getInventory() == null || ws.getInventory() <= wsOrderAdd.getQuantity()) {
            throw new BizException(ExCode.error, "库存不足！");
        }
        String now = DateTool.now();

        PriceComputeDTO.PriceComputeDTOBuilder priceComputeBuilder = PriceComputeDTO.builder();
        priceComputeBuilder.unitPrice(ws.getUnitPrice());
        priceComputeBuilder.quantity(wsOrderAdd.getQuantity());
        priceComputeBuilder.couponPrice(BigDecimal.valueOf(6.8));
        PriceComputeDTO priceComputeDTO = priceComputeBuilder.build();
        OrderTool.computePrice(priceComputeDTO);


        WSOrder wsOrder = new WSOrder();
        wsOrder.init(IdTool.getId(), IdTool.getCode(), ws.getName(), ws.getWsId(), now, wsOrderAdd.getUserId());
        wsOrder.setCouponCode(wsOrderAdd.getCouponCode());
        wsOrder.setPrice(priceComputeDTO.getTotal());
        wsOrder.setUnitPrice(priceComputeDTO.getUnitPrice());
        wsOrder.setQuantity(priceComputeDTO.getQuantity());
        wsOrder.setReduce(priceComputeDTO.getCouponPrice());
        wsOrder.setProductCode(ws.getWsCode());
        //do update ops
        ws.deduceInventory(Long.valueOf(wsOrder.getQuantity()), now);
        AtomicBoolean flag = new AtomicBoolean(false);
        int cycleCount = 0;
        try {
            do {
                if (kappLock.lock(wsId, TimeUnit.MILLISECONDS, 1500)) {
                    flag.set(true);
                    wsRepository.save(ws);
                    wsOrderRepository.save(wsOrder);
                    TransactionUtil.committedCallback(() ->
                            wsOrderMessageCenter.addOrderMessage(TimeKappMessage.builder()
                                    .id(wsOrder.getOrderId())
                                    //set up order wait pay time
                                    .duration(Duration.of(2, ChronoUnit.MINUTES).toMillis())
                                    .messageType(MessageType.TIME)
                                    .build()));
                }
                cycleCount++;
            } while (!flag.get() && cycleCount < 5);
        } finally {
            kappLock.unlock(wsId);
        }
    }
}