package com.kapp.kappcore.task.job;

import com.kapp.kappcore.service.domain.repository.LineMsItemRepository;
import com.kapp.kappcore.task.support.produce.LineMsProducer;
import org.springframework.core.task.AsyncTaskExecutor;

public abstract class AbstractTransferDb {
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

}
