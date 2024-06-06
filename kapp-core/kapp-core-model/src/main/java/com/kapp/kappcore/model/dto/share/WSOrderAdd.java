package com.kapp.kappcore.model.dto.share;

import lombok.Data;

@Data
public class WSOrderAdd {
    private String wsId;
    private String userId;
    private Integer quantity;
    private String couponCode;
}
