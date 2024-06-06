package com.kapp.kappcore.model.entity.share;

import com.kapp.kappcore.model.dto.share.pay.PayStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tb_order_info", schema = "work_station")
public class WSOrder {
    @Id
    private String orderId;
    private String orderCode;
    private String name;
    private String productCode;
    private String status;
    private String unit;

    private BigDecimal price;
    private Integer quantity;
    private BigDecimal unitPrice;
    private String payTime;
    private String couponCode;

    private String businessCode;
    private String userId;
    private String createTime;
    private String updateTime;
    private BigDecimal reduce;

    public void initOrder(String orderId, String orderCode, String name, String businessCode, String now, String userId) {
        setOrderId(orderId);
        setOrderCode(orderCode);
        setStatus(PayStatus.UN_PAY.getCode());
        setBusinessCode(businessCode);
        setName(name);
        setUserId(userId);
        setCreateTime(now);
        setUpdateTime(now);
    }
}
