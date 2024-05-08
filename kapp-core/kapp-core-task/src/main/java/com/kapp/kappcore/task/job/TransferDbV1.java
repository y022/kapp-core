package com.kapp.kappcore.task.job;

import cn.hutool.core.collection.ListUtil;
import com.kapp.kappcore.domain.repository.LineMsItemRepository;
import com.kapp.kappcore.model.entity.LineMsItem;
import com.kapp.kappcore.task.support.produce.LineMsProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class TransferDbV1 extends AbstractTransferDb {


    public TransferDbV1(AsyncTaskExecutor asyncTaskExecutor, LineMsProducer lineMsProducer,
                        LineMsItemRepository lineMsItemRepository, TransferElasticSearch transferElasticSearch) {
        super(asyncTaskExecutor, lineMsProducer, lineMsItemRepository, transferElasticSearch);
    }

    @Override
    public void transfer(String tag) {
        StopWatch stopWatch = new StopWatch("transfer" + tag);
        stopWatch.start("read");
        log.info("开始准备读取数据....");
        lineMsProducer.prepareItem(tag);
        stopWatch.stop();
        log.info("数据读取完毕,读取耗时:" + stopWatch.getLastTaskTimeMillis());

        stopWatch.start("write");

        List<LineMsItem> items = lineMsProducer.produceByType(-1);

        List<List<LineMsItem>> coll = ListUtil.split(items, BATCH_SIZE);

        CountDownLatch cdl = new CountDownLatch(coll.size());


        transferElasticSearch.reset();
        for (List<LineMsItem> executeItems : coll) {
            asyncTaskExecutor.execute(() -> {
                try {
                    save(executeItems);
                } finally {
                    cdl.countDown();
                }
            });
        }

        try {
            cdl.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        print(stopWatch);
        transferElasticSearch.computeTime();

    }

}
