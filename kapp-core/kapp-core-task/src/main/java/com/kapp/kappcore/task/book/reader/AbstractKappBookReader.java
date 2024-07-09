package com.kapp.kappcore.task.book.reader;

import com.kapp.kappcore.model.dto.book.BookMeta;
import com.kapp.kappcore.model.entity.book.Book;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Author:Heping
 * Date: 2024/7/9 18:23
 */
public abstract class AbstractKappBookReader implements KappBookReader {
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");
    private static final String VERSION = "V1.0";

    protected AtomicLong getCounter() {
        return new AtomicLong();
    }

    protected boolean shouldSave(String content) {
        return !StringUtils.isBlank(content);
    }

    protected Book warpBook(String content, BookMeta bookMeta, long contentSort) {
        Book book = new Book();
        String createTime = LocalDateTime.now().format(df);
        book.updateTime(createTime, createTime);
        book.setTag(bookMeta.getTag());
        book.setContent(content.trim());
        book.setContentSort(contentSort);
        book.setAuthor(bookMeta.getAuthor());
        book.setTitle(bookMeta.getTitle());
        book.setVersion(VERSION);
        String id = computeMd5(book.getContent(), book.getTitle(), String.valueOf(book.getContentSort()));
        if (id.length() > 64) {
            id = id.substring(0, 63);
        }
        book.setId(id);
        return book;
    }

    protected String computeMd5(String... str) {
        String input = String.join("_", str);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not find MD5 algorithm", e);
        }

    }

}
