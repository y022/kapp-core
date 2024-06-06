package com.kapp.kappcore.web.vo.share.order.ws;

import com.kapp.kappcore.model.page.PageAndSort;
import lombok.Data;

@Data
public class WSQueryRequestVo {
    private String wsId;
    private String wsCode;
    private String name;
    private PageAndSort pageAndSort;
}
