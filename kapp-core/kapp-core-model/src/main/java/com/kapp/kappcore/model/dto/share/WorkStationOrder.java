package com.kapp.kappcore.model.dto.share;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class WorkStationOrder implements Order {
    private String orderCode;
    private String name;
    private String status;
    private String price;
    private String unit;
    private String quantity;
    private String unitPrice;
    private String businessCode;
    private String businessName;
    private String userId;
    private String userName;
    private String createTime;
    private String updateTime;
    private String payTime;

    @Override
    public void updateTime() {
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String orderCode() {
        return getOrderCode();
    }

    @Override
    public void updateStatus(String status) {
        setStatus(status);
    }

}
