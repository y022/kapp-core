package com.kapp.kappcore.task.support.produce;

import com.kapp.kappcore.model.entity.ExecuteItem;
import com.kapp.kappcore.model.entity.LineMsItem;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
public class LineMsProducer extends AbstractProducer {
    private final Path path;
    @Getter
    private final Queue<LineMsItem> queue;
    private final String version = Long.toString(System.currentTimeMillis());

    public LineMsProducer(String path) {
        Objects.requireNonNull(path);
        this.path = Paths.get(path);
        this.queue = new ArrayBlockingQueue<>(100000000, false);
    }

    @Override
    public ExecuteItem produce() {
        return queue.poll();
    }

    @Override
    public void prepareItem(String tag) {
        try {
            read(tag);
        } catch (Exception e) {
            log.error("lineMsProducer prepare error", e);
        }
    }

    @Override
    public List<ExecuteItem> produce(int capacity) {
        List<ExecuteItem> items = new ArrayList<>();
        if (queue.isEmpty()) {
            return items;
        }
        int k = capacity < 0 ? queue.size() : Math.min(capacity, queue.size());
        for (int i = 0; i < k; i++) {
            items.add(queue.poll());
        }
        return items;
    }

    public List<LineMsItem> produceByType(int capacity) {

        List<LineMsItem> items = new ArrayList<>();
        return getLineMsItems(capacity, items);
    }

    private List<LineMsItem> getLineMsItems(int capacity, List<LineMsItem> items) {
        if (queue.isEmpty()) {
            return items;
        }
        int k = capacity < 0 ? queue.size() : Math.min(capacity, queue.size());
        for (int i = 0; i < k; i++) {
            items.add(queue.poll());
        }
        return items;
    }


    private void read(String tag) throws IOException {
        if (Files.exists(path)) {
            File file = path.toFile();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.lines().forEach(line -> {
                LineMsItem lineMsItem = new LineMsItem();
                String id = UUID.randomUUID().toString();
                lineMsItem.setId(id);
                lineMsItem.setNo("No." + id);
                String content = new String(line.getBytes(), StandardCharsets.UTF_8);
                lineMsItem.setContent(content);
                lineMsItem.setVersion(version);
                lineMsItem.setTag(tag);
                lineMsItem.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS")));
                queue.offer(lineMsItem);
            });
        } else {
            throw new FileNotFoundException(path + "is not found");
        }
    }

}
