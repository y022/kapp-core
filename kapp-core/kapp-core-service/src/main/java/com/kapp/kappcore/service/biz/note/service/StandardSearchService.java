package com.kapp.kappcore.service.biz.note.service;

import com.kapp.kappcore.model.biz.request.SearchRequestDTO;
import com.kapp.kappcore.service.biz.note.dto.ComplexSearchResultDTO;
import com.kapp.kappcore.service.biz.note.dto.GroupSearchDTO;
import com.kapp.kappcore.service.biz.note.dto.GroupSearchResultDTO;

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
