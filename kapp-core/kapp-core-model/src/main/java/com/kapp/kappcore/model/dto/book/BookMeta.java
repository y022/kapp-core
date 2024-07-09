package com.kapp.kappcore.model.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:Heping
 * Date: 2024/7/9 18:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookMeta {
    private String path;
    private String title;
    private String tag;
    private String author;
}
