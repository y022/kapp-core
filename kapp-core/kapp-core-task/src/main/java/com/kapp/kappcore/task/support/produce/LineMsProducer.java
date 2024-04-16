package com.kapp.kappcore.task.support.produce;

import com.kapp.kappcore.entity.ExecuteItem;
import com.kapp.kappcore.entity.LineMsItem;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
public class LineMsProducer extends AbstractProducer {
    private final Path path;
    private final Queue<LineMsItem> queue;
    private final String version = UUID.randomUUID().toString();

    public LineMsProducer(String path) {
        Objects.requireNonNull(path);
        this.path = Paths.get(path);
        this.queue = new ArrayBlockingQueue<>(10000, false);
    }

    @Override
    public ExecuteItem produce() {
        return queue.poll();
    }

    @Override
    public void prepare() {
        try {
            read();
        } catch (Exception e) {
            log.error("lineMsProducer prepare error", e);
        }
    }

    @Override
    public List<ExecuteItem> produce(int capacity) {
        List<ExecuteItem> items = new ArrayList<>();
        if (queue.isEmpty() || capacity <= 0) {
            return items;
        }
        for (int i = 0; i < capacity; i++) {
            items.add(queue.poll());
        }
        return null;
    }

    private void read() throws IOException {
        boolean exists = Files.exists(path);
        if (exists) {
            File file = path.toFile();
            BufferedReader reader = new BufferedReader(new FileReader(file));

            reader.lines().forEach(line -> {
                LineMsItem lineMsItem = new LineMsItem();

                String id = UUID.randomUUID().toString();

                lineMsItem.setId(id);

                lineMsItem.setNo("No." + id);

                lineMsItem.setContent(line);

                lineMsItem.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                queue.offer(lineMsItem);
            });


        }
    }

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("format = " + format);
    }
}
