package com.kapp.kappcore.web.vo.share.order.ws;

import com.kapp.kappcore.model.dto.share.ws.WSDTO;
import com.kapp.kappcore.model.page.PageAndSort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WSQueryResponseVo {
    private List<WSDTO> ws;
    private PageAndSort pageAndSort;
}
