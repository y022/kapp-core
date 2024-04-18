package com.kapp.kappcore.task.support.produce;

import com.kapp.kappcore.model.entity.ExecuteItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

class LineMsProducerTest {

    @Test
    void prepare() {
        LineMsProducer lineMsProducer = new LineMsProducer("/Users/y022/Downloads/fbcfc7b1474e9b34833f538f28b380d5.txt");
        lineMsProducer.prepareItem("fr");
        List<ExecuteItem> res = lineMsProducer.produce(10);
        System.out.println("res = " + res);
    }

    @Test
    void testDate(){
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS"));
        System.out.println("format = " + format);
    }
}