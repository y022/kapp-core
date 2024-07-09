package com.kapp.kappcore.task.book;

import com.kapp.kappcore.model.dto.book.BookMeta;

/**
 * Author:Heping
 * Date: 2024/7/9 18:07
 */

public interface KappBookSaveService {

    void save(String path,BookMeta bookMeta);
}
