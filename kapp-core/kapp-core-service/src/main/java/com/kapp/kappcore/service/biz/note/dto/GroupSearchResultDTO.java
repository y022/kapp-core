package com.kapp.kappcore.service.biz.note.dto;

import com.kapp.kappcore.model.biz.domain.group.Group;
import com.kapp.kappcore.model.biz.domain.group.GroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupSearchResultDTO {
    private long groupCount;
    private GroupType groupType;
    private List<Group> group;
}
