package com.kapp.kappcore.model.entity.book;

import com.kapp.kappcore.model.entity.RepositoryBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tb_book_info", schema = "public")
@EqualsAndHashCode(callSuper = true)
public class BookInfo extends RepositoryBean {
    @Id
    private String id;
    private String bookMd5;
}
