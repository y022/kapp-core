package com.kapp.kappcore.service.biz.note.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapp.kappcore.model.biz.domain.search.SearchResult;
import com.kapp.kappcore.model.biz.request.SearchRequestDTO;
import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.constant.SearchVal;
import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.service.biz.note.dto.ComplexSearchResultDTO;
import com.kapp.kappcore.service.biz.note.dto.GroupSearchDTO;
import com.kapp.kappcore.service.biz.note.dto.GroupSearchResultDTO;
import com.kapp.kappcore.service.biz.note.search.context.DocSearchContext;
import com.kapp.kappcore.service.biz.note.search.context.GroupSearchContext;
import com.kapp.kappcore.service.biz.note.search.context.obj.SearchSource;
import com.kapp.kappcore.service.biz.note.search.core.search.KappStandardSearcher;
import com.kapp.kappcore.service.biz.note.search.support.convert.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class StandardSearchServiceImpl implements StandardSearchService {
    private final KappStandardSearcher iStandardSearcher;
    private final MapperFacade mapperFacade;
    private final ObjectMapper objectMapper;

    @Override
    public ComplexSearchResultDTO normalSearch(SearchRequestDTO request) {
        DocSearchContext searchContext = new DocSearchContext();
        SearchSource searchSource = mapperFacade.map(request, SearchSource.class);
        searchContext.wireSearchVal(searchSource, request.getSearchPage(), request.getSearchSize(), SearchVal.SEARCH_TITLE, SearchVal.SEARCH_BODY);
        SearchResult<?> searchResult = iStandardSearcher.search(searchContext);
        List<ComplexSearchResultDTO.ComplexSearchDTO> contents = searchResult.searchBody().stream().map(item -> {
            String body = item.body();
            try {
                ComplexSearchResultDTO.ComplexSearchDTO resultDTO = objectMapper.readValue(body, ComplexSearchResultDTO.ComplexSearchDTO.class);
                Map<String, Object> highlightContent = item.highlightContent();
                Class<? extends ComplexSearchResultDTO.ComplexSearchDTO> clz = resultDTO.getClass();
                searchContext.getHighlightFields().forEach(highlight -> Converter.convertHighlight(highlight, highlightContent, clz, resultDTO));
                return resultDTO;
            } catch (Exception e) {
                log.error("search result disSer error", e);
                throw new SearchException(ExCode.search_result_get_error, "获取检索结果失败!");
            }
        }).collect(Collectors.toList());
        return new ComplexSearchResultDTO(searchResult.getTotal(), searchResult.getTook(), contents);
    }

    @Override
    public GroupSearchResultDTO groupSearch(GroupSearchDTO request) {
        GroupSearchContext context = mapperFacade.map(request, GroupSearchContext.class);
        context.onlyTag(request.getTag());
        SearchResult<?> group = iStandardSearcher.group(context);

        return null;
    }
}
