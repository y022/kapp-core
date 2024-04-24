package com.kapp.kappcore.task.job;

import com.kapp.kappcore.domain.repository.LineMsItemRepository;
import com.kapp.kappcore.model.entity.LineMsItem;
import com.kapp.kappcore.task.support.produce.LineMsProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class TransferDbV2 extends AbstractTransferDb {
    public TransferDbV2(AsyncTaskExecutor asyncTaskExecutor, LineMsProducer lineMsProducer, LineMsItemRepository lineMsItemRepository, TransferElasticSearch transferElasticSearch) {
        super(asyncTaskExecutor, lineMsProducer, lineMsItemRepository, transferElasticSearch);
    }

    @Override
    public void transfer(String tag) {

        log.info("开始准备读取数据....");
        lineMsProducer.prepareItem(tag);
        log.info("读取数据完毕....");


        StopWatch stopWatch = new StopWatch("transfer" + tag);

        stopWatch.start("write");

        Queue<LineMsItem> queue = lineMsProducer.getQueue();

        AtomicBoolean ab = new AtomicBoolean(false);

        CountDownLatch cdl = new CountDownLatch(16);

        for (int i = 0; i < 16; i++) {
            asyncTaskExecutor.execute(() -> {
                try {
                    while (!queue.isEmpty()) {
                        if (ab.compareAndExchange(false, true)) {
                            List<LineMsItem> m = lineMsProducer.produceByType(2000);
                            ab.compareAndExchange(true, false);
                            try {
                                lineMsItemRepository.saveAll(m);
                                transferElasticSearch.transfer(m);
                            } catch (Exception e) {
                                log.error("error:", e);
                            }
                        }
                    }
                } finally {
                    cdl.countDown();
                }
            });
        }

        try {
            cdl.await();
        } catch (InterruptedException e) {
            log.error("InterruptedException:", e);
        }

        stopWatch.stop();

        log.info("数据插入完毕,插入耗时:" + stopWatch.getLastTaskTimeMillis());
        log.info("任务总耗时:" + stopWatch.getTotalTimeMillis());
        log.info(stopWatch.prettyPrint());
    }
}
