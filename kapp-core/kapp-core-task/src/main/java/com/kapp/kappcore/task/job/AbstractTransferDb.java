package com.kapp.kappcore.task.job;

import com.kapp.kappcore.domain.repository.LineMsItemRepository;
import com.kapp.kappcore.model.entity.LineMsItem;
import com.kapp.kappcore.task.support.produce.LineMsProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.List;

@Slf4j
public abstract class AbstractTransferDb {
    protected static final int BATCH_SIZE = 1000;

    protected final AsyncTaskExecutor asyncTaskExecutor;
    protected final LineMsProducer lineMsProducer;
    protected final LineMsItemRepository lineMsItemRepository;
    protected final TransferElasticSearch transferElasticSearch;

    public AbstractTransferDb(AsyncTaskExecutor asyncTaskExecutor, LineMsProducer lineMsProducer, LineMsItemRepository lineMsItemRepository, TransferElasticSearch transferElasticSearch) {
        this.asyncTaskExecutor = asyncTaskExecutor;
        this.lineMsProducer = lineMsProducer;
        this.lineMsItemRepository = lineMsItemRepository;
        this.transferElasticSearch = transferElasticSearch;
    }


    public abstract void transfer(String tag);


    protected void save(List<LineMsItem> items) {
        try {
            lineMsItemRepository.saveAll(items);
            transferElasticSearch.transfer(items);
        } catch (IOException e) {
            log.error("error:", e);
        }
    }

    protected static void print(StopWatch stopWatch) {
        stopWatch.stop();
        log.info("数据插入完毕,插入耗时:" + stopWatch.getLastTaskTimeMillis());
        log.info("任务总耗时:" + stopWatch.getTotalTimeMillis());
        log.info(stopWatch.prettyPrint());
    }
}
