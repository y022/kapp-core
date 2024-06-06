package com.kapp.kappcore.model.entity.share;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@Table(name = "tb_ws_info", schema = "work_station")
@AllArgsConstructor
@NoArgsConstructor
public class WS {
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
    private String name;
    /**
     * 单价
     */
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
    private String description;
    /**
     * 商家ID
     */
    private String businessId;

    public void deduceInventory(Long inventory,String updateTime) {
        setInventory(this.inventory - inventory);
        setUpdateTime(updateTime);
    }
}
