package com.kapp.kappcore.task.job;

import com.kapp.kappcore.model.entity.book.Book;
import com.kapp.kappcore.model.entity.book.BookInfo;
import com.kapp.kappcore.service.domain.mapper.BookMapper;
import com.kapp.kappcore.support.tool.date.DateTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableScheduling
public class BookJob {

    private static final ArrayBlockingQueue<BookInfo> queue = new ArrayBlockingQueue<>(40000000);
    private static final ArrayList<BookInfo> bookInfos = new ArrayList<>(1000);
    private final BookMapper bookMapper;
    private volatile String idCursor = "000000ecdfc8e549d859f21bae616849";
    private static final AtomicReferenceFieldUpdater<BookJob, String> counter = AtomicReferenceFieldUpdater.newUpdater(BookJob.class, String.class, "idCursor");
    private final AsyncTaskExecutor asyncTaskExecutor;

    @Scheduled(fixedRate = 3000)
    public void produce() {
        List<Book> books = bookMapper.select(5000, counter.get(this));
        books.parallelStream().forEach(book -> {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setId(book.getId());
            String now = DateTool.now();
            bookInfo.updateTime(now, now);
            String md5 = computeMd5(book.getId() + book.getContent() + book.getTitle() + book.getAuthor());
            bookInfo.setBookMd5(md5);
            queue.add(bookInfo);
        });
        counter.set(this, books.get(books.size() - 1).getId());
    }

    @PostConstruct
    public void consume() throws InterruptedException {
        asyncTaskExecutor.execute(() -> {
            while (true) {
                BookInfo take = null;
                try {
                    take = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bookInfos.add(take);
                if (bookInfos.size() >= 2000) {
                    bookMapper.insertInfo(bookInfos);
                    bookInfos.clear();
                }
            }
        });
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
