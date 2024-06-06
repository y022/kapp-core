package com.kapp.kappcore.service.biz.note.search.context;

import com.kapp.kappcore.model.biz.domain.group.GroupType;
import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.service.biz.note.search.context.obj.SearchSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GroupSearchContext extends AbstractSearchContext {
    private String groupField;
    private Set<String> groupFields;
    private GroupType groupType;

    @Override
    public boolean requireHighlight() {
        return false;
    }

    @Override
    public Set<String> highlightFields() {
        throw new SearchException(ExCode.search_condition_error, "not support highlight!");
    }

    public void onlyTag(String tag) {
        SearchSource searchSource = new SearchSource();
        searchSource.setTag(tag);
        setSource(searchSource);
    }
}
