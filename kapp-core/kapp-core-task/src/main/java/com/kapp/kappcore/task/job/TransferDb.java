package com.kapp.kappcore.task.job;

import com.kapp.kappcore.domain.repository.LineMsItemRepository;
import com.kapp.kappcore.model.entity.ExecuteItem;
import com.kapp.kappcore.model.entity.LineMsItem;
import com.kapp.kappcore.task.support.ExecutePoint;
import com.kapp.kappcore.task.support.produce.LineMsProducer;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransferDb {

    private final AsyncTaskExecutor asyncTaskExecutor;
    private final LineMsProducer lineMsProducer;
    private final LineMsItemRepository lineMsItemRepository;
    private final ExecutePoint<LineMsItem> executePoint = new ExecutePoint<>() {
        @Override
        public void execute(LineMsItem t) {
            lineMsItemRepository.save(t);
        }
    };

    public TransferDb(AsyncTaskExecutor asyncTaskExecutor, LineMsItemRepository lineMsItemRepository) {
        this.asyncTaskExecutor = asyncTaskExecutor;
        this.lineMsProducer = new LineMsProducer("/Users/y022/Downloads/fbcfc7b1474e9b34833f538f28b380d5.txt");
        this.lineMsItemRepository = lineMsItemRepository;
//        asyncTaskExecutor.execute(this::start);
    }

    public void start() {
        lineMsProducer.prepareItem();
        List<ExecuteItem> items = lineMsProducer.produce(-1);
        for (ExecuteItem item : items) {
            executePoint.execute((LineMsItem) item);
        }
    }

}
