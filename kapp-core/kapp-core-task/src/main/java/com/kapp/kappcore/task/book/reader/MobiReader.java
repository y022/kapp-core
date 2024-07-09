package com.kapp.kappcore.task.book.reader;

import com.kapp.kappcore.model.dto.book.BookMeta;
import com.kapp.kappcore.model.dto.book.BookType;
import com.kapp.kappcore.model.entity.book.Book;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.function.Consumer;

/**
 * Author:Heping
 * Date: 2024/7/9 18:03
 */
@Component
public class MobiReader extends AbstractKappBookReader {
    @Override
    public boolean support(BookType bookType) {
        return BookType.MOBI.equals(bookType);
    }

    @Override
    public void read(Path path, BookMeta bookMeta , Consumer<Book> consumer) {

    }
}
