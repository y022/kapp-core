package com.kapp.kappcore.service.domain.mapper;

import com.kapp.kappcore.model.entity.book.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/7/11 13:27
 */
@Mapper
public interface BookMapper {
    void insert(@Param("books") Set<Book> set);
}
