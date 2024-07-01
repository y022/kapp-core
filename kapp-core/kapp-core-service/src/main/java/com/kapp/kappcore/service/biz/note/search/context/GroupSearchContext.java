package com.kapp.kappcore.service.biz.note.search.context;

import com.kapp.kappcore.model.biz.domain.group.GroupType;
import com.kapp.kappcore.service.biz.note.search.context.obj.SearchSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GroupSearchContext extends AbstractSearchContext {
    /**
     * group type
     */
    private GroupType groupType;
    /**
     * group fields
     */
    private Set<String> groupFields;
    /**
     * subGroup fields,belong to groupFields
     */
    private Map<String, Set<String>> subGroupFields;

    @Override
    public boolean requireHighlight() {
        return false;
    }

    @Override
    public Set<String> highlightFields() {
        return Set.of();
    }

    public void tag(String tag) {
        SearchSource searchSource = new SearchSource();
        searchSource.setTag(tag);
        setSource(searchSource);
    }


}
