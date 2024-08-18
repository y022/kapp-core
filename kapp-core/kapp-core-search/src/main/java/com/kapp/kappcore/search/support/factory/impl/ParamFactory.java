package com.kapp.kappcore.search.support.factory.impl;

import com.kapp.kappcore.search.common.ExtSearchRequest;
import com.kapp.kappcore.search.support.factory.AbstractParamFactory;
import com.kapp.kappcore.search.support.model.SearchLimiter;
import com.kapp.kappcore.search.support.model.condition.AbstractSearchCondition;
import com.kapp.kappcore.search.support.model.condition.ValCondition;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.model.param.SortParam;
import com.kapp.kappcore.search.support.model.param.ViewParam;
import com.kapp.kappcore.search.support.option.DocOption;
import com.kapp.kappcore.search.support.option.ViewType;
import com.kapp.kappcore.search.support.option.sort.SortRule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Author:Heping
 * Date: 2024/6/25 16:02
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParamFactory extends AbstractParamFactory {
    private MultiQuerySentenceFactory querySentenceFactory = MultiQuerySentenceFactory.getInstance();
    private static final ParamFactory INSTANCE = new ParamFactory();

    public static ParamFactory instance() {
        return INSTANCE;
    }

    public SearchParam create() {
        return new SearchParam();
    }

    public SearchParam create(ExtSearchRequest extSearchRequest) {
        requiredNonNull(extSearchRequest);
        SearchParam searchParam = create();
        searchParam.setIndex((extSearchRequest.getIndex()));
        searchParam.setEnableScroll(extSearchRequest.isEnableScroll());
        searchParam.setScrollId(extSearchRequest.getScrollId());

        if (searchParam.continueScroll()) {
            searchParam.setCondition(AbstractSearchCondition.scroll(searchParam.getIndex()));
            return searchParam;
        } else {
            ValCondition valCondition = initCondition(extSearchRequest);
            searchParam.setCondition(valCondition);
            searchParam.setSearchLimiter(createLimiter(extSearchRequest));
        }
        return searchParam;
    }

    private ValCondition initCondition(ExtSearchRequest extSearchRequest) {
        DocOption docOption = DocOption.codeOf(extSearchRequest.getDocOption());

        ValCondition valCondition = null;
        switch (Objects.requireNonNull(docOption)) {
            case SEARCH:
                valCondition = search(extSearchRequest);
                break;
            case GROUP:
                valCondition = group(extSearchRequest);
                break;
            case DELETE:
            case UPDATE:
                valCondition = update(extSearchRequest, docOption);
                break;
        }
        if (valCondition != null)
            valCondition.index(extSearchRequest.getIndex());
        return valCondition;
    }


    private SearchLimiter createLimiter(ExtSearchRequest extSearchRequest) {
        SearchLimiter searchLimiter = new SearchLimiter();
        searchLimiter.setPageNum(extSearchRequest.getPageNum());
        searchLimiter.setPageSize(extSearchRequest.getPageSize());
        searchLimiter.setShowScore(extSearchRequest.isShowScore());

        ViewParam viewParam = new ViewParam();
        viewParam.setViewType(ViewType.codeOf(extSearchRequest.getViewType()));
        viewParam.setViewFields(extSearchRequest.getViewFields());
        searchLimiter.setViewParam(viewParam);

        searchLimiter.setSortRule(SortRule.codeOf(extSearchRequest.getSortRule()));
        searchLimiter.setSortParams(SortParam.toParam(extSearchRequest.getSortMap()));

        return searchLimiter;
    }
}
