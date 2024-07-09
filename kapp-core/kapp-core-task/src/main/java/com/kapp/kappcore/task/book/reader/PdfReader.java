package com.kapp.kappcore.task.book;

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
public class PdfReader implements KappBookReader{
    @Override
    public boolean support(BookType bookType) {
        return false;
    }

    @Override
    public void read(BookType bookType, Path path, Consumer<Book> consumer) {

    }
}
