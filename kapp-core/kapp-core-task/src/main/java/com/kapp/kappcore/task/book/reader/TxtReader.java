package com.kapp.kappcore.task.book.reader;

import com.kapp.kappcore.model.dto.book.BookMeta;
import com.kapp.kappcore.model.dto.book.BookType;
import com.kapp.kappcore.model.entity.book.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * Author:Heping
 * Date: 2024/7/9 18:02
 */
@Slf4j
@Component
public class TxtReader extends AbstractKappBookReader {

    @Override
    public boolean support(BookType bookType) {
        return BookType.TXT.equals(bookType);
    }

    @Override
    public void read(Path path, BookMeta bookMeta, Consumer<Book> consumer) {
        File file = path.toFile();
        AtomicLong counter = getCounter();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String content;
            while ((content = bufferedReader.readLine()) != null) {
                if (shouldSave(content)) {
                    Book book = warpBook(content, bookMeta, counter.addAndGet(1));
                    consumer.accept(book);
                }
            }
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            log.info("save complete,title:{}", bookMeta.getTitle());
        }
    }
}
