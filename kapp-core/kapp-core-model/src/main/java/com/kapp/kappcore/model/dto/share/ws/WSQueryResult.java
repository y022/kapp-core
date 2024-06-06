package com.kapp.kappcore.model.dto.share.ws;

import com.kapp.kappcore.model.page.PageAndSort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WSQueryResult {
    private List<WSDTO> ws;
    private PageAndSort pageAndSort;
}
