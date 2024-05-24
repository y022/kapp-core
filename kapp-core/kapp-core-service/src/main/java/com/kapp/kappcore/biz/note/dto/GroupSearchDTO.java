package com.kapp.kappcore.biz.note.dto;

import com.kapp.kappcore.model.biz.domain.group.GroupType;
import lombok.Data;

import java.util.Set;

@Data
public class GroupSearchDTO {
    private String groupField;
    private Set<String> groupFields;
    private GroupType groupType;
    private String tag;
}
