package com.kapp.kappcore.web.vo;

import com.kapp.kappcore.model.biz.domain.group.GroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchGroupRequestVo {
    private String tag;
    private String groupField;
    private Set<String> groupFields;
    private GroupType groupType;
}
