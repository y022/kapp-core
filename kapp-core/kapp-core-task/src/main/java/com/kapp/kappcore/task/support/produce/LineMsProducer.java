package com.kapp.kappcore.task.support.produce;

import com.kapp.kappcore.model.entity.ExecuteItem;
import com.kapp.kappcore.model.entity.LineMsItem;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
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
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

@Slf4j
public class LineMsProducer extends AbstractProducer {
    private final Path path;
    @Getter
    private final Queue<LineMsItem> queue;
    private final String version = Long.toString(System.currentTimeMillis());
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");
    private static final Object OBJ = new Object();

    public LineMsProducer(String path) {
        Objects.requireNonNull(path);
        this.path = Paths.get(path);
        this.queue = new ArrayDeque<>(100000000);
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


    private void read(String tag) throws IOException {
        if (Files.exists(path)) {
            BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
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

    private void read1(String tag) throws IOException {
        if (Files.exists(path)) {
//            String date = LocalDateTime.now().format(df);
            BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
            reader.lines().forEach(line -> {
                LineMsItem lineMsItem = new LineMsItem();
//                String id = UUID.randomUUID().toString();
//                lineMsItem.setId(id);
//                lineMsItem.setNo("No." + id);
//                String content = new String(line.getBytes(), StandardCharsets.UTF_8);
//                lineMsItem.setContent(content);
//                lineMsItem.setVersion(version);
//                lineMsItem.setTag(tag);
//                lineMsItem.setDate(date);
                queue.offer(lineMsItem);
            });
        } else {
            throw new FileNotFoundException(path + "is not found");
        }
    }

    private void read2(String tag) throws IOException {
        ReentrantLock reentrantLock = new ReentrantLock();
        if (Files.exists(path)) {

            RandomAccessFile file = new RandomAccessFile(path.toFile(), "r");
            FileChannel fileChannel = file.getChannel();
            // 映射文件到ByteBuffer，这里映射整个文件
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
            // 读取文件内容
            byte[] bytes = new byte[(int) fileChannel.size()];
            mappedByteBuffer.get(bytes); // 将ByteBuffer中的数据读取到字节数组中
            // 将字节数组转换为字符串，以便打印或进一步处理

            String content = new String(bytes, StandardCharsets.UTF_8);

            Pattern pattern = Pattern.compile("\r\n");
            Arrays.stream(pattern.split(content)).parallel().forEach(str -> {
                LineMsItem lineMsItem = new LineMsItem();
                String id = UUID.randomUUID().toString();
                lineMsItem.setId(id);
                lineMsItem.setNo("No." + id);
                lineMsItem.setContent(str);
                lineMsItem.setVersion(version);
                lineMsItem.setTag(tag);
                lineMsItem.setDate(LocalDateTime.now().format(df));
                synchronized (OBJ) {
                    queue.offer(lineMsItem);
                }
            });
            log.info("共读取数据条数：" + queue.size());
            fileChannel.close();
            file.close();
        } else {
            throw new FileNotFoundException(path + "is not found");
        }
    }
}
