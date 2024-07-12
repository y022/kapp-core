package com.kapp.kappcore.task.book.reader;

import com.kapp.kappcore.model.dto.book.BookMeta;
import com.kapp.kappcore.model.dto.book.BookType;
import com.kapp.kappcore.model.entity.book.Book;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

/**
 * Author:Heping
 * Date: 2024/7/9 18:03
 */
@Slf4j
@Component
public class KappPdfReader extends AbstractKappBookReader {
    @Override
    public boolean support(BookType bookType) {
        return BookType.PDF.equals(bookType);
    }

    @Override
    public void read(Path path, BookMeta bookMeta, Consumer<Book> consumer) {
//        try (PDDocument document = PDDocument.load(path.toFile())) {
//            PDFTextStripper stripper = new PDFTextStripper();
//            int numberOfPages = document.getNumberOfPages();
//
//            for (int i = 1; i <= numberOfPages; i++) {
//                try {
//                    stripper.setStartPage(i);
//                    stripper.setEndPage(i);
//                    String text = stripper.getText(document);
//                    if (!text.isEmpty() && shouldSave(text)) {
//                        Book book = warpBook(text, bookMeta, text.length());
//                        consumer.accept(book);
//                    }
//                } catch (Throwable e) {
//                    log.error("error:",e);
//                }
//            }
//        } catch (IOException e) {
//            log.error("Error reading PDF with PDFBox", e);
//        }
    }


}
