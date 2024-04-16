package com.kapp.kappcore.biz.note.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapp.kappcore.biz.note.dto.ComplexSearchDTO;
import com.kapp.kappcore.biz.note.dto.ComplexSearchResultDTO;
import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.biz.note.search.context.ComplexSearchContext;
import com.kapp.kappcore.biz.note.search.context.obj.SearchSource;
import com.kapp.kappcore.biz.note.search.core.IStandardSearcher;
import com.kapp.kappcore.biz.note.search.support.result.SearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class StandardSearchServiceImpl implements StandardSearchService {

    private final IStandardSearcher iStandardSearcher;
    private final MapperFacade mapperFacade;
    private final ObjectMapper objectMapper;

    @Override
    public List<ComplexSearchResultDTO> complexSearch(ComplexSearchDTO searchDTO) {

        ComplexSearchContext searchContext = new ComplexSearchContext();

        SearchSource searchSource = new SearchSource();
        mapperFacade.map(searchDTO, searchSource);


        searchContext.setSource(searchSource);
        SearchResult result = iStandardSearcher.search(searchContext);

        //这里忽略一些检索结果细节参数，只返回具有业务含义的结果
        return result.body().stream().map(item -> {
            String body = item.getBody();
            try {
                return objectMapper.readValue(body, ComplexSearchResultDTO.class);
            } catch (JsonProcessingException e) {
                log.error("search result disSer error", e);
                throw new SearchException(ExCode.search_result_get_error, "获取检索结果失败!");
            }
        }).collect(Collectors.toList());
    }
}
