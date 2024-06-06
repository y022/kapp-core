package com.kapp.kappcore.web.api.biz.search;

import com.kapp.kappcore.model.biz.request.SearchRequestDTO;
import com.kapp.kappcore.service.biz.note.dto.ComplexSearchResultDTO;
import com.kapp.kappcore.service.biz.note.dto.GroupSearchDTO;
import com.kapp.kappcore.service.biz.note.dto.GroupSearchResultDTO;
import com.kapp.kappcore.service.biz.note.service.StandardSearchService;
import com.kapp.kappcore.web.vo.SearchGroupRequestVo;
import com.kapp.kappcore.web.vo.response.SearchGroupResponseVo;
import com.kapp.kappcore.web.vo.SearchRequestVo;
import com.kapp.kappcore.web.vo.response.SearchRespVo;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/standardSearch")
@RequiredArgsConstructor
public class StandardSearchApi {
    private final StandardSearchService standardSearchService;
    private final MapperFacade mapperFacade;

    @PostMapping("/normal")
    SearchRespVo normalSearch(@RequestBody SearchRequestVo searchReq) {
        ComplexSearchResultDTO complexSearchResultDTO = standardSearchService.normalSearch(mapperFacade.map(searchReq, SearchRequestDTO.class));
        return mapperFacade.map(complexSearchResultDTO, SearchRespVo.class);
    }

    @PostMapping("/group")
    SearchGroupResponseVo groupSearch(@RequestBody SearchGroupRequestVo groupRequest) {
        GroupSearchResultDTO groupSearchResultDTO = standardSearchService.groupSearch(mapperFacade.map(groupRequest, GroupSearchDTO.class));
        return mapperFacade.map(groupSearchResultDTO, SearchGroupResponseVo.class);
    }

}
