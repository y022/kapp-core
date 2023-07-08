package com.kapp.kappcore.web.search;

import com.kapp.kappcore.web.vo.SearchReqVo;
import com.kapp.kappcore.web.vo.response.SearchResultVo;
import com.kapp.kappcore.domain.dto.ComplexSearchDTO;
import com.kapp.kappcore.service.StandardSearchService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/standardSearch")
@RequiredArgsConstructor
public class StandardSearchApi {

    private final StandardSearchService standardSearchService;
    private final MapperFacade mapperFacade;

    @PostMapping("/complex")
    List<SearchResultVo> complexSearch(@RequestBody SearchReqVo reqVo) {
        return mapperFacade.mapAsList(standardSearchService.complexSearch(new ComplexSearchDTO(reqVo.getTitle(), reqVo.getBody(), reqVo.getTag(), reqVo.getSaveDate(), reqVo.getOwner())),
                SearchResultVo.class);
    }

}
