package com.kappcore.service;

import com.kappcore.domain.dto.ComplexSearchDTO;
import com.kappcore.domain.map.ComplexSearchResult;
import com.kappcore.manager.main.Searcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandardSearchServiceImpl implements StandardSearchService {

    private final Searcher searcher;

    public StandardSearchServiceImpl(Searcher searcher) {
        this.searcher = searcher;
    }

    @Override
    public List<ComplexSearchResult> complexSearch(ComplexSearchDTO searchDTO) {




        return null;
    }
}
