package com.kapp.kappcore.model.entity.share;

import com.kapp.kappcore.model.dto.share.pay.PayStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "tb_order_info", schema = "work_station")
public class WSOrder extends RepositoryBean {
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
    private BigDecimal reduce;

    public void init(String orderId, String orderCode, String name, String businessCode, String now, String userId) {
        setOrderId(orderId);
        setOrderCode(orderCode);
        setStatus(PayStatus.UN_PAY.getCode());
        setBusinessCode(businessCode);
        setName(name);
        setUserId(userId);
        updateTime(now, now);
    }

    public void updateStatus(String status) {
        if (status != null && !status.isEmpty()) {
            setStatus(status);
        }
    }

    public void updateStatus(String status, String updateTime) {
        updateStatus(status);
        updateTime(null, updateTime);
    }

}
