package com.kapp.kappcore.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "table_line_ms",schema = "public")
public class LineMsItem extends ExecuteItem {
    @Id
    private String id;
    private String no;
    private String content;
    private String version;
    private String date;
}
