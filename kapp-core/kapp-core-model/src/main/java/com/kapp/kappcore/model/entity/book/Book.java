package com.kapp.kappcore.model.entity.book;

import com.kapp.kappcore.model.entity.RepositoryBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author:Heping
 * Date: 2024/7/9 17:49
 */
@Data
@Entity
@Table(name = "tb_book", schema = "public")
@EqualsAndHashCode(callSuper = true)
public class Book extends RepositoryBean {
    @Id
    private String id;
    private String title;
    private String content;
    private String author;
    private String tag;
    @Column(name = "content_sort")
    private long contentSort;
    private String version;
    private boolean deleted = false;
}
