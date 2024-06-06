package com.kapp.kappcore.model.dto.share;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceComputeDTO {
    private BigDecimal unitPrice;
    private Integer quantity;
    private BigDecimal couponPrice;
    private BigDecimal total;




    public static PriceComputeDTO create() {
        return new PriceComputeDTO();
    }


}
