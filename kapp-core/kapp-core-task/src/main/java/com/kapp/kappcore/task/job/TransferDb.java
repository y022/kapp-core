package com.kapp.kappcore.task.job;

import cn.hutool.core.collection.ListUtil;
import com.kapp.kappcore.domain.repository.LineMsItemRepository;
import com.kapp.kappcore.model.entity.ExecuteItem;
import com.kapp.kappcore.model.entity.LineMsItem;
import com.kapp.kappcore.task.support.ExecutePoint;
import com.kapp.kappcore.task.support.produce.LineMsProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        lineMsProducer.prepareItem(tag);

        List<ExecuteItem> items = lineMsProducer.produce(-1);

        for (List<ExecuteItem> executeItems : ListUtil.split(items, 2000)) {
            asyncTaskExecutor.execute(() -> {
                ArrayList<LineMsItem> m = new ArrayList<>();
                executeItems.forEach(v -> m.add((LineMsItem) v));
                try {
                    executePoint.execute(m);
                } catch (Exception e) {
                    log.error("error", e);
                }
                try {
                    transferElasticSearch.transfer(m);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
