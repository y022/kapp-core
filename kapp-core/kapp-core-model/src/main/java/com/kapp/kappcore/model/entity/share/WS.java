package com.kapp.kappcore.model.entity.share;

import com.kapp.kappcore.model.entity.RepositoryBean;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "tb_ws_info", schema = "work_station")
public class WS extends RepositoryBean {
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
     * 描述
     */
    private String description;
    /**
     * 商家ID
     */
    private String businessId;

    public void deduceInventory(Long inventory, String updateTime) {
        setInventory(this.inventory - inventory);
        setUpdateTime(updateTime);
    }
}
