package com.kappcore.service;

import com.kappcore.domain.dto.ComplexSearchDTO;
import com.kappcore.domain.map.ComplexSearchResult;

import java.util.List;

public interface StandardSearchService {
    /**
     * 提供复杂的检索方式
     *
     * @param searchDTO 检索实体
     * @return 检索结果
     */
    List<ComplexSearchResult> complexSearch(ComplexSearchDTO searchDTO);
}
