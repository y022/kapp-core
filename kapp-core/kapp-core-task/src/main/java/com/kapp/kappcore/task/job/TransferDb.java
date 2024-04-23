package com.kapp.kappcore.task.job;

import cn.hutool.core.collection.ListUtil;
import com.kapp.kappcore.domain.repository.LineMsItemRepository;
import com.kapp.kappcore.model.entity.ExecuteItem;
import com.kapp.kappcore.model.entity.LineMsItem;
import com.kapp.kappcore.task.support.ExecutePoint;
import com.kapp.kappcore.task.support.produce.LineMsProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class TransferDb {

    private final AsyncTaskExecutor asyncTaskExecutor;
    private final LineMsProducer lineMsProducer;
    private final LineMsItemRepository lineMsItemRepository;
    private final TransferElasticSearch transferElasticSearch;

    private final ExecutePoint<List<LineMsItem>> executePoint = new ExecutePoint<>() {
        @Override
        public void execute(List<LineMsItem> t) {
            lineMsItemRepository.saveAll(t);
        }
    };


    public TransferDb(AsyncTaskExecutor asyncTaskExecutor, LineMsProducer lineMsProducer, LineMsItemRepository lineMsItemRepository, TransferElasticSearch transferElasticSearch) {
        this.asyncTaskExecutor = asyncTaskExecutor;
        this.lineMsProducer = lineMsProducer;
        this.lineMsItemRepository = lineMsItemRepository;
        this.transferElasticSearch = transferElasticSearch;
    }

    public void start(String tag) {
        StopWatch stopWatch = new StopWatch("transfer" + tag);
        stopWatch.start("read");
        log.info("开始准备读取数据....");
        lineMsProducer.prepareItem(tag);
        stopWatch.stop();
        log.info("数据读取完毕,读取耗时:" + stopWatch.getLastTaskTimeMillis());


        stopWatch.start("write");

        List<ExecuteItem> items = lineMsProducer.produce(-1);

        List<List<ExecuteItem>> coll = ListUtil.split(items, 1000);

        CountDownLatch cdl = new CountDownLatch(coll.size());

        for (List<ExecuteItem> executeItems : coll) {
            asyncTaskExecutor.execute(() -> {
                ArrayList<LineMsItem> m = new ArrayList<>();
                executeItems.forEach(v -> m.add((LineMsItem) v));
                try {
                    executePoint.execute(m);
                    transferElasticSearch.transfer(m);
                } catch (Exception e) {
                    log.error("error:", e);
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

        stopWatch.stop();

        log.info("数据插入完毕,插入耗时:" + stopWatch.getLastTaskTimeMillis());
        log.info("任务总耗时:" + stopWatch.getTotalTimeMillis());
        log.info(stopWatch.prettyPrint());
    }
}
