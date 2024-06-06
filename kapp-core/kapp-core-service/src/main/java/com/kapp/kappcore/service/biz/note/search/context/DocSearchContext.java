package com.kapp.kappcore.service.biz.note.search.context;

import com.kapp.kappcore.service.biz.note.search.context.obj.SearchSource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class DocSearchContext extends AbstractSearchContext {

    protected Set<String> highlightFields = new HashSet<>();

    @Override
    public boolean requireHighlight() {
        return getSource() != null && getSource().isHighlight();
    }

    @Override
    public Set<String> highlightFields() {
        return this.getHighlightFields();
    }

    public void wireSearchVal(SearchSource source, int searchPage, int searchSize, String... highlight) {
        super.wireSearchVal(source, searchPage, searchSize);
        if (highlight != null) setHighlightFields(Set.of(highlight));
    }

    @Override
    public boolean emptyValue() {
        return super.emptyValue() || (!StringUtils.hasText(source.getBody()) && !StringUtils.hasText(source.getTitle()));
    }
}
