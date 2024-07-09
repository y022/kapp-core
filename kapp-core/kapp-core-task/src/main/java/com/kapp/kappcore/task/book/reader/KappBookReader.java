package com.kapp.kappcore.task.book.reader;

import com.kapp.kappcore.model.dto.book.BookMeta;
import com.kapp.kappcore.model.dto.book.BookType;
import com.kapp.kappcore.model.entity.book.Book;

import java.nio.file.Path;
import java.util.function.Consumer;

/**
 * Author:Heping
 * Date: 2024/7/9 17:55
 */

public interface KappBookReader {
    boolean support(BookType bookType);
    void read(Path path, BookMeta bookMeta , Consumer<Book> consumer);


}
