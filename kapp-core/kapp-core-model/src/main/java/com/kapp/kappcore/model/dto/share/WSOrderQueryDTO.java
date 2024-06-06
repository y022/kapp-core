package com.kapp.kappcore.model.dto.share;

import com.kapp.kappcore.model.page.PageAndSort;
import lombok.Data;

@Data
public class WSOrderQueryDTO {
    private String status;
    private String name;
    private String userId;
    private String wsId;
    private PageAndSort pageAndSort;
}
