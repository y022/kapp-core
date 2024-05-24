package com.kapp.kappcore.biz.note.service;

import com.kapp.kappcore.biz.note.dto.ComplexSearchResultDTO;
import com.kapp.kappcore.biz.note.dto.GroupSearchDTO;
import com.kapp.kappcore.biz.note.dto.GroupSearchResultDTO;
import com.kapp.kappcore.model.biz.request.SearchRequestDTO;

public interface StandardSearchService {
    /**
     * 提供复杂的检索方式
     *
     * @param searchDTO 检索实体
     * @return 检索结果
     */
    ComplexSearchResultDTO normalSearch(SearchRequestDTO searchDTO);

    GroupSearchResultDTO groupSearch(GroupSearchDTO searchDTO);
}
