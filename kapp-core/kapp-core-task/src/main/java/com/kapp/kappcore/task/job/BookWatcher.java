package com.kapp.kappcore.task.job;

import com.kapp.kappcore.model.constant.DocKey;
import com.kapp.kappcore.model.entity.book.Book;
import com.kapp.kappcore.search.endpoint.UpdateServiceImpl;
import com.kapp.kappcore.service.biz.note.search.index.TagIndex;
import com.kapp.kappcore.service.domain.mapper.BookMapper;
import com.kapp.kappcore.support.mq.MqProducer;
import com.kapp.kappcore.support.mq.MqRouteMapping;
import com.kapp.kappcore.support.mq.annotation.MqConsumer;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

/**
 * Author:Heping
 * Date: 2024/7/9 20:00
 */
@Slf4j
@Component
public class BookWatcher {
    private static final Queue<Book> BOOK_QUEUE = new ArrayBlockingQueue<>(10000000);
    private final RestHighLevelClient restHighLevelClient;
    private final MqProducer mqProducer;
    private final BookMapper bookMapper;
    private final UpdateServiceImpl updateServiceImplService;
    @Value("${batchSize:100}")
    public int batchSize;


    public BookWatcher(RestHighLevelClient restHighLevelClient, MqProducer mqProducer, BookMapper bookMapper, UpdateServiceImpl updateServiceImplService) {
        this.restHighLevelClient = restHighLevelClient;
        this.mqProducer = mqProducer;
        this.bookMapper = bookMapper;
        this.updateServiceImplService = updateServiceImplService;

    }

    @MqConsumer(queue = {MqRouteMapping.Queue.SAVE_BOOK}, concurrency = "1")
    public void watchMessage(Book book) {
        if (book != null) {
            BOOK_QUEUE.add(book);
        }
        if (BOOK_QUEUE.size() >= batchSize) {
            Set<Book> books = new HashSet<>(400);
            for (int i = 0; i < batchSize; i++) {
                books.add(BOOK_QUEUE.poll());
            }
            try {
                long start = System.currentTimeMillis();
                bookMapper.insert(books);
                log.info("数据库插入耗时：{}", System.currentTimeMillis() - start);
            } catch (Exception e) {
                log.error("save pg error:", e);
                reSend(books);
            }
            saveSearch(books);
        }
    }

    public void saveSearch(Set<Book> books) {
        List<Map<String, Object>> dataMap = books.stream().map(book -> {
            Map<String, Object> map = new HashMap<>();
            map.put(DocKey.ID, book.getId());
            map.put(DocKey.TITLE, book.getTitle());
            map.put(DocKey.CONTENT, Arrays.stream(book.getContent().split("。")).toArray(String[]::new));
            map.put(DocKey.CONTENT_SORT, book.getContentSort());
            map.put(DocKey.TAG, book.getTag());
            map.put(DocKey.VERSION, book.getVersion());
            map.put(DocKey.DELETED, book.isDeleted());
            map.put(DocKey.CREATE_TIME, book.getCreateTime());
            map.put(DocKey.UPDATE_TIME, book.getUpdateTime());
            map.put(DocKey.CONTENT_LENGTH, book.getContent().length());
            return map;
        }).collect(Collectors.toList());
        updateServiceImplService.update_async(dataMap, TagIndex.BOOK.getIndex());
    }

    private void reSend(Set<Book> books) {
        log.warn("reSend message...");
        books.forEach(v -> mqProducer.send(MqRouteMapping.Exchange.BOOK, MqRouteMapping.RoutingKey.SAVE_BOOK_RETRY, v));
    }
}
