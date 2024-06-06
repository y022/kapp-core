package com.kapp.kappcore.web.vo.share.order.order;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderAddRequestVo {
    @NotEmpty(message = "wsId not exists!")
    private String wsId;
    @NotEmpty(message = "userId  not exists!")
    private String userId;
    @NotEmpty(message = "quantity  not exists!")
    private Integer quantity;
    private String couponCode;
}
