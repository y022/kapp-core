package com.kapp.kappcore.task.support.produce;

import com.kapp.kappcore.model.entity.ExecuteItem;
import org.junit.jupiter.api.Test;

class LineMsProducerTest {

    @Test
    void prepare() {
        LineMsProducer lineMsProducer = new LineMsProducer("/Users/y022/Desktop/docker_note.txt");
        lineMsProducer.prepare();
        ExecuteItem produce = lineMsProducer.produce();
    }
}