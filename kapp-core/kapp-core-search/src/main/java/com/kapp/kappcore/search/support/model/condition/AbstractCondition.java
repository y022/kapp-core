package com.kapp.kappcore.search.support.model.condition;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.configuration.SearchConfiguration;
import com.kapp.kappcore.search.support.option.DocOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/6/23 17:18
 */
@Getter
@NoArgsConstructor
public abstract class AbstractCondition implements ValCondition {
    @Setter
    protected Set<String> index;

    @Setter
    protected DocOption option;
    protected SearchConfiguration.SearchField searchField;

    public AbstractCondition(Set<String> index, DocOption option) {
        this.index = index;
        this.option = option;
    }

    @Override
    public Set<String> index() {
        return this.index;
    }

    public AbstractCondition(Set<String> index, DocOption option,
                             SearchConfiguration.SearchField searchField) {
        this.index = index;
        this.option = option;
        this.searchField = searchField;
    }

    @Override
    public DocOption option() {
        return getOption();
    }

    public static ValCondition nullCondition() {
        return new NullCondition();
    }

    public static class NullCondition extends AbstractCondition {
        public NullCondition() {
            super();
        }

        @Override
        public Set<String> getIndex() {
            return null;
        }

        @Override
        public DocOption getOption() {
            return null;
        }

        @Override
        public void setOption(DocOption option) {
            // noting to do
        }

        public NullCondition(Set<String> index, DocOption option) {
            super(index, option);
        }

        @Override
        public DocOption option() {
            return null;
        }

        @Override
        public Set<String> index() {
            return Set.of();
        }

        @Override
        public String read() {
            return "";
        }

        @Override
        public void checkAndCompensate() throws SearchException {

        }
    }
}
