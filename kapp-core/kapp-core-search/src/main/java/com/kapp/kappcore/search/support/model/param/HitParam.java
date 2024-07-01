package com.kapp.kappcore.search.support.model.param;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.support.Checker;
import com.kapp.kappcore.search.support.option.ContHitStrategy;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/6/24 22:33
 */
@Data
@NoArgsConstructor
public class HitParam implements Checker {
    /**
     * 命中策略
     */
    private ContHitStrategy contHitStrategy;
    /**
     * 通配符
     */
    private String wildcard;
    /**
     * 默认左通配
     */
    private boolean leftWildcard = true;
    /**
     * 默认不进行全通配
     */
    protected boolean allWildcard = false;

    /**
     * 需要做特殊检索处理的字段，比如分词，模糊查询等操作
     */
    private Set<String> fields;

    public HitParam(ContHitStrategy contHitStrategy, String wildcard, Set<String> fields) {
        this.contHitStrategy = contHitStrategy;
        this.wildcard = wildcard;
        this.fields = fields;
    }

    public static HitParam accurate() {
        HitParam hitParam = new HitParam();
        hitParam.setContHitStrategy(ContHitStrategy.ACCURATE);
        return hitParam;
    }

    @Override
    public void checkAndCompensate() throws SearchException {
        ContHitStrategy hitStrategy = getContHitStrategy();
        if (hitStrategy != null) {
            if (hitStrategy == ContHitStrategy.WILDCARD) {
                if (StringUtils.isBlank(wildcard)) {
                    throw new SearchException(ExCode.search_condition_error, "wildcard-search must bo hava valid wildcard");
                }
            }
        }
    }
}
