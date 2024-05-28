package com.kapp.kappcore.web.vo.share.order;

import com.kapp.kappcore.model.dto.share.WorkStationOrder;
import com.kapp.kappcore.model.page.PageAndSort;
import lombok.Data;

@Data
public class OrderQueryResponseVo {
    private WorkStationOrder order;
    private PageAndSort pageAndSort;
}
