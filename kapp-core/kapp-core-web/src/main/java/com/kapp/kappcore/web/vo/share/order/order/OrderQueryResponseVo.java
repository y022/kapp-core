package com.kapp.kappcore.web.vo.share.order.order;

import com.kapp.kappcore.model.dto.share.WSOrderDTO;
import com.kapp.kappcore.model.page.PageAndSort;
import lombok.Data;

import java.util.List;

@Data
public class OrderQueryResponseVo {
    private List<WSOrderDTO> orders;
    private PageAndSort pageAndSort;
}
