package com.kappcore.service;

import com.kappcore.domain.dto.ComplexSearchDTO;
import com.kappcore.domain.map.ComplexSearchResult;

import java.util.List;

public interface StandardSearchService {
    List<ComplexSearchResult> complexSearch(ComplexSearchDTO searchDTO);
}
