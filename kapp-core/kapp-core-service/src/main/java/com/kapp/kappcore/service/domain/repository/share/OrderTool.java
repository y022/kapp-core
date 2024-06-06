package com.kapp.kappcore.service.domain.repository.share;

import com.kapp.kappcore.model.dto.share.PriceComputeDTO;

import java.math.BigDecimal;

public class OrderTool {

    public static void computePrice(PriceComputeDTO priceComputeDTO) {

        BigDecimal couponPrice = priceComputeDTO.getCouponPrice();

        BigDecimal preTotal = priceComputeDTO.getUnitPrice().multiply(BigDecimal.valueOf(priceComputeDTO.getQuantity()));

        priceComputeDTO.setTotal(preTotal.subtract(couponPrice));


    }
}
