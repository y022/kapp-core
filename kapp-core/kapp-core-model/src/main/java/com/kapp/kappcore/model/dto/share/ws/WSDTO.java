package com.kapp.kappcore.model.dto.share.ws;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WSDTO {
    /**
     * 工位ID
     */
    @Id
    private String wsId;
    /**
     * 工位编号
     */
    private String wsCode;
    /**
     * 工位名称
     */
    @NotNull(message = "name not empty!")
    private String name;
    /**
     * 单价
     */
    @NotEmpty(message = "unitPrice not empty!")
    private BigDecimal unitPrice;
    /**
     * 库存
     */
    private Long inventory;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 描述
     */
    @NotEmpty(message = "description not empty!")
    private String description;
}
