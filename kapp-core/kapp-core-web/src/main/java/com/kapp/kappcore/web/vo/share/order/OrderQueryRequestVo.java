package com.kapp.kappcore.web.vo.share.order;

import com.kapp.kappcore.model.page.PageAndSort;
import lombok.Data;

@Data
public class OrderQueryRequestVo {
    private String userId;
    private String status;
    private String name;
    private PageAndSort pageAndSort;
}
