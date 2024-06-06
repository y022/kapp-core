package com.kapp.kappcore.model.dto.share.ws;

import com.kapp.kappcore.model.page.PageAndSort;
import lombok.Data;

@Data
public class WSQueryDTO {
    private String wsId;
    private String wsCode;
    private String name;
    private PageAndSort pageAndSort;
}
