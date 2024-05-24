package com.kapp.kappcore.web.vo.response;


import com.kapp.kappcore.model.biz.domain.group.Group;
import com.kapp.kappcore.model.biz.domain.group.GroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchGroupResponseVo {
    private long groupCount;
    private GroupType groupType;
    private List<Group> group;
}
