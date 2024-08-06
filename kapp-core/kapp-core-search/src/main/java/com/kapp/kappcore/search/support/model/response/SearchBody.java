package com.kapp.kappcore.search.support.model.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Heping
 * Date: 2024/6/23 16:22
 */
@Data
public class SearchBody extends AbstractBody {
    private List<?> data;

    @Override
    public List<?> data() {
        return data == null ? new ArrayList<>(0) : data;
    }

}
