package com.kapp.kappcore.service.biz.note.dto;

import com.kapp.kappcore.model.biz.domain.group.GroupType;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class GroupSearchDTO {
    private String tag;
    private GroupType groupType;
    private Set<String> groupFields;
    private Map<String, Object> groupKV;
}
