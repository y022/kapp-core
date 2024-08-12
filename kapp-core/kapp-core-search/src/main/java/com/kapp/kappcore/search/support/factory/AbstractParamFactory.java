package com.kapp.kappcore.search.support.factory;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.common.ExtSearchRequest;
import com.kapp.kappcore.search.support.model.condition.GroupCondition;
import com.kapp.kappcore.search.support.model.condition.SearchCondition;
import com.kapp.kappcore.search.support.model.condition.UpdateCondition;
import com.kapp.kappcore.search.support.model.condition.ValCondition;
import com.kapp.kappcore.search.support.option.DocOption;

public abstract class AbstractParamFactory {

    protected void requiredNonNull(ExtSearchRequest extSearchRequest) {
        if (extSearchRequest == null) {
            throw new SearchException("request param entity is null");
        }
    }


    protected ValCondition search(ExtSearchRequest extSearchRequest) {
        SearchCondition sc = new SearchCondition();
        sc.setSearchAll(extSearchRequest.isSearchAll());
        sc.setSearchValueMap(extSearchRequest.getSearchValueMap());
        sc.setSearchParamUnits(extSearchRequest.getSearchParamUnits());
        sc.setOption(DocOption.SEARCH);
        return sc;
    }

    protected ValCondition group(ExtSearchRequest extSearchRequest) {
        GroupCondition gc = new GroupCondition();
        gc.setGroupParamUnits(extSearchRequest.getGroupParamUnits());
        gc.setOption(DocOption.GROUP);

        return gc;
    }

    protected ValCondition update(ExtSearchRequest extSearchRequest, DocOption docOption) {
        UpdateCondition uc = new UpdateCondition(docOption);
        uc.setUpdateMap(extSearchRequest.getUpdateValueMap());
        uc.setDelIds(extSearchRequest.getDeleteIds());
        uc.setIndex(extSearchRequest.getIndex());
        uc.setOption(DocOption.UPDATE);

        return uc;

    }
}
