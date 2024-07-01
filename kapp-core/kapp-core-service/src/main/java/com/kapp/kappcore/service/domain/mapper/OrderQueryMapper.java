package com.kapp.kappcore.service.domain.mapper;

/**
 * Author:Heping
 * Date: 2024/6/16 1:32
 */

import com.kapp.kappcore.model.entity.share.WSOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderQueryMapper {
    List<WSOrder> query();
}
