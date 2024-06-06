package com.kapp.kappcore.model.dto.share;

import com.kapp.kappcore.model.page.PageAndSort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WSOrderQueryResult {
    private List<WSOrderDTO> orders;
    private PageAndSort pageAndSort;

}
