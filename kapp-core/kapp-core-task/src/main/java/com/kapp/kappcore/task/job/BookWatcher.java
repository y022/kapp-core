package com.kapp.kappcore.task.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapp.kappcore.model.constant.DocKey;
import com.kapp.kappcore.model.entity.book.Book;
import com.kapp.kappcore.service.biz.note.search.index.TagIndex;
import com.kapp.kappcore.support.mq.MqProducer;
import com.kapp.kappcore.support.mq.MqRouteMapping;
import com.kapp.kappcore.support.mq.annotation.MqConsumer;
import com.kapp.kappcore.task.order.domain.reposity.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Author:Heping
 * Date: 2024/7/9 20:00
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BookWatcher {
    private static final ArrayBlockingQueue<Book> BOOK_QUEUE = new ArrayBlockingQueue<>(100000);
    private final BookRepository bookRepository;
    private final ObjectMapper ob = new ObjectMapper();
    private final RestHighLevelClient restHighLevelClient;
    private final MqProducer mqProducer;
    private static final AtomicInteger counter = new AtomicInteger(1);

    @MqConsumer(queue = MqRouteMapping.Queue.SAVE_BOOK, concurrency = "2")
    public void watch(Book book) {
        if (book != null) {
            BOOK_QUEUE.add(book);
        }
        if (BOOK_QUEUE.size() >= 100) {
            ArrayList<Book> books = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                books.add(BOOK_QUEUE.poll());
            }

            try {
                bookRepository.saveAll(books);
            } catch (Exception e) {
                reSend(books);
            }
            saveSearch(books);
        }
    }

    @Scheduled(fixedRate = 1000 * 5)
    public void loopSaveEs() {
        Page<Book> books = bookRepository.findAll((PageRequest.of(counter.get(), 600)));
        List<Book> list = books.toList();
        if (CollectionUtils.isNotEmpty(list)) {
            saveSearch(list);
            counter.addAndGet(1);
        }
    }


    public void saveSearch(List<Book> books) {
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

        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String, Object> itemMap : dataMap) {
            IndexRequest indexRequest = new IndexRequest(TagIndex.BOOK.getIndex(), "_doc", itemMap.get(DocKey.ID).toString());
            IndexRequest source = indexRequest.source(itemMap);
            bulkRequest.add(source);
        }

        restHighLevelClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, new ActionListener<>() {
            @Override
            public void onResponse(BulkResponse bulkItemResponses) {
                log.info("一批数据成功处理完毕，数量：" + dataMap.size());
            }
            @Override
            public void onFailure(Exception e) {
                reSend(books);
            }
        });
    }

    private void reSend(List<Book> books) {
        log.warn("reSend message...");
        books.forEach(v -> mqProducer.send(MqRouteMapping.Exchange.BOOK, MqRouteMapping.RoutingKey.SAVE_BOOK_RETRY, v));
    }
}
