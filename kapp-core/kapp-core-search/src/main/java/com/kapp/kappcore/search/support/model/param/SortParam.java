package com.kapp.kappcore.search.support.model.param;

import com.kapp.kappcore.search.support.option.sort.SortType;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author:Heping
 * Date: 2024/7/5 15:24
 */
@Data
public class SortParam {
    private String sortKey;
    private SortType sortType;

    public static List<SortParam> toParam(Map<String, String> sortMap) {
        if (sortMap == null || sortMap.isEmpty()) {
            return List.of();
        }

        return sortMap.entrySet().stream().map(entry -> {
            SortParam sortParam = new SortParam();
            sortParam.setSortKey(entry.getKey());
            sortParam.setSortType(SortType.codeOf(entry.getValue()));
            return sortParam;
        }).collect(Collectors.toList());


    }

}
