package com.kapp.kappcore.search.support.builder.impl;

import com.kapp.kappcore.search.common.SearchRequestDTO;
import com.kapp.kappcore.search.support.builder.SearchFactory;
import com.kapp.kappcore.search.support.DateTool;
import com.kapp.kappcore.search.support.index.IndexChooser;
import com.kapp.kappcore.search.support.model.SearchLimiter;
import com.kapp.kappcore.search.support.model.condition.GroupCondition;
import com.kapp.kappcore.search.support.model.condition.SearchCondition;
import com.kapp.kappcore.search.support.model.condition.ValCondition;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.model.param.ViewParam;
import com.kapp.kappcore.search.support.option.DocOption;
import com.kapp.kappcore.search.support.option.ViewType;
import com.kapp.kappcore.search.support.option.sort.SortRule;
import com.kapp.kappcore.search.support.option.sort.SortType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Author:Heping
 * Date: 2024/6/25 16:02
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParamConstructor implements SearchFactory<SearchRequestDTO, SearchParam> {
    private static final ParamConstructor INSTANCE = new ParamConstructor();

    public static ParamConstructor instance() {
        return INSTANCE;
    }

    public SearchParam create() {
        return new SearchParam();
    }

    private static void setFlag(SearchParam searchParam) {
        searchParam.setSearchId(UUID.randomUUID().toString());
        searchParam.setSearchTime(DateTool.now());
    }

    public SearchParam create(SearchRequestDTO searchRequestDTO) {
        SearchParam searchParam = create();
        Set<String> indexes = IndexChooser.index(searchRequestDTO.getTag());
        searchParam.setIndexes(indexes);
        DocOption docOption = DocOption.codeOf(searchRequestDTO.getDocOption());
        ValCondition valCondition;
        switch (Objects.requireNonNull(docOption)) {
            case SEARCH:
                valCondition = searchCondition(searchRequestDTO);
                break;
            case GROUP:
                valCondition = group(searchRequestDTO);
                break;
            default:
                // todo other-condition
                valCondition = null;
        }
        searchParam.setCondition(valCondition);
        searchParam.setSearchLimiter(createLimiter(searchRequestDTO));
        setFlag(searchParam);
        return searchParam;
    }

    private ValCondition searchCondition(SearchRequestDTO searchRequestDTO) {
        SearchCondition searchCondition = new SearchCondition();
        searchCondition.setSearchAll(searchRequestDTO.isSearchAll());
        searchCondition.setHasMultiCondition(CollectionUtils.isNotEmpty(searchRequestDTO.getSearchParamUnits()));
        searchCondition.setOption(DocOption.SEARCH);
        searchCondition.setSearchValueMap(searchRequestDTO.getSearchValueMap());
//        if (StringUtils.isBlank(searchRequestDTO.getWildcard())) {
////            searchCondition.setHitParam(HitParam.accurate(searchRequestDTO.get()));
//        } else {
//            searchCondition.setHitParam(new HitParam(ContHitStrategy.codeOf(searchRequestDTO.getContHitStrategy()), searchRequestDTO.getWildcard(), searchRequestDTO.getSortKeys()));
//        }
        return searchCondition;
    }

    private ValCondition group(SearchRequestDTO searchRequestDTO) {
        GroupCondition updateCondition = new GroupCondition();
        updateCondition.setOption(DocOption.GROUP);
        updateCondition.setGroupParamUnits(searchRequestDTO.getGroupParamUnits());
        updateCondition.setBucketCount(100);
        updateCondition.setSubBucketCount(10);
        updateCondition.setBucketGroup(true);
        updateCondition.setIndex(searchRequestDTO.getTag());
        return updateCondition;
    }

    private SearchLimiter createLimiter(SearchRequestDTO searchRequestDTO) {
        SearchLimiter searchLimiter = new SearchLimiter();
        searchLimiter.setPageNum(searchRequestDTO.getPageNum());
        searchLimiter.setPageSize(searchRequestDTO.getPageSize());
        searchLimiter.setShowScore(searchRequestDTO.isShowScore());

        ViewParam viewParam = new ViewParam();
        viewParam.setViewType(ViewType.codeOf(searchRequestDTO.getViewType()));
        viewParam.setViewFields(searchRequestDTO.getViewFields());
        searchLimiter.setViewParam(viewParam);

        searchLimiter.setSortRule(SortRule.codeOf(searchRequestDTO.getSortRule()));
        searchLimiter.setSortType(SortType.codeOf(searchRequestDTO.getSortType()));
        searchLimiter.setSortKeys(searchRequestDTO.getSortKeys());
        return searchLimiter;
    }
}
