package com.kapp.kappcore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapp.kappcore.constant.ExCode;
import com.kapp.kappcore.domain.dto.ComplexSearchDTO;
import com.kapp.kappcore.domain.dto.ComplexSearchResultDTO;
import com.kapp.kappcore.exception.SearchException;
import com.kapp.kappcore.search.context.ComplexSearchContext;
import com.kapp.kappcore.search.context.obj.SearchSource;
import com.kapp.kappcore.search.core.IStandardSearcher;
import com.kapp.kappcore.search.support.result.SearchResult;
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

        searchContext.setSource(mapperFacade.map(searchDTO, SearchSource.class));

        SearchResult result = iStandardSearcher.search(searchContext);

        //这里忽略一些检索结果细节参数，只返回具有业务含义的结果
        return result.body().stream().map(item -> {
            try {
                return objectMapper.readValue(item.getBody(), ComplexSearchResultDTO.class);
            } catch (JsonProcessingException e) {
                log.error("search result disSer error", e);
                throw new SearchException(ExCode.search_result_get_error, "获取检索结果失败!");
            }
        }).collect(Collectors.toList());
    }
}
