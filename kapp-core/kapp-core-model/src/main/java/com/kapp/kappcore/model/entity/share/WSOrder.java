package com.kapp.kappcore.model.entity.share;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tb_order_info", schema = "work_station")
public class WSOrder {
    @Id
    private String id;
    private String orderCode;
    private String name;
    private String productCode;
    private String status;
    private String price;
    private String unit;
    private String quantity;
    private String unitPrice;
    private String businessCode;
    private String userId;
    private String createTime;
    private String updateTime;
    private String payTime;
    private String couponCode;
    private String reduce;
}
