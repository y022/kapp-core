package com.kapp.kappcore.service;

import com.kapp.kappcore.api.standarnd.ComplexSearchDTO;
import com.kapp.kappcore.domain.dto.ComplexSearchResultDTO;

import java.util.List;

public interface StandardSearchService {
    /**
     * 提供复杂的检索方式
     *
     * @param searchDTO 检索实体
     * @return 检索结果
     */
    List<ComplexSearchResultDTO> complexSearch(ComplexSearchDTO searchDTO);
}
