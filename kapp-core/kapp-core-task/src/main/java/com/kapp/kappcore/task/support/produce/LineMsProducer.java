package com.kapp.kappcore.task.support.produce;

import com.kapp.kappcore.model.entity.ExecuteItem;
import com.kapp.kappcore.model.entity.LineMsItem;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Pattern;

@Slf4j
public class LineMsProducer extends AbstractProducer {
    private final Path path;
    @Getter
    private final Queue<LineMsItem> queue;
    private final String version = Long.toString(System.currentTimeMillis());
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");
    private static final Pattern pattern = Pattern.compile("\n");

    public LineMsProducer(String path) {
        Objects.requireNonNull(path);
        this.path = Paths.get(path);
        this.queue = new ArrayBlockingQueue<>(100000000);
    }

    @Override
    public ExecuteItem produce() {
        return queue.poll();
    }

    @Override
    public void prepareItem(String tag) {
        try {
            read2(tag);
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

    private void read2(String tag) throws IOException {
        if (Files.exists(path)) {
            RandomAccessFile file = new RandomAccessFile(path.toFile(), "r");
            FileChannel fileChannel = file.getChannel();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
            byte[] bytes = new byte[(int) fileChannel.size()];
            mappedByteBuffer.get(bytes);
            String content = new String(bytes, StandardCharsets.UTF_8);
            Arrays.stream(pattern.split(content)).parallel().filter(StringUtils::hasText).forEach(str -> {
                LineMsItem lineMsItem = new LineMsItem();
                String id = UUID.randomUUID().toString();
                lineMsItem.setId(id);
                lineMsItem.setNo("No." + id);
                str = str.trim();
                lineMsItem.setContent(str.length() > 1024 ? str.substring(0, 1024) : str);
                lineMsItem.setVersion(version);
                lineMsItem.setTag(tag);
                lineMsItem.setDate(LocalDateTime.now().format(df));
                queue.add(lineMsItem);
            });
            log.info("共读取数据条数：" + queue.size());
            fileChannel.close();
            file.close();
        } else {
            throw new FileNotFoundException(path + " is not found");
        }
    }


}
