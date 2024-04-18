package com.kapp.kappcore.web.api.task;

import com.kapp.kappcore.domain.repository.LineMsItemRepository;
import com.kapp.kappcore.task.job.TransferDb;
import com.kapp.kappcore.task.job.TransferElasticSearch;
import com.kapp.kappcore.task.support.produce.LineMsProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferApi {
    private final TransferElasticSearch transferElasticSearch;
    private final AsyncTaskExecutor asyncTaskExecutor;
    private final LineMsItemRepository lineMsItemRepository;

    @GetMapping("/book")
    public void transfer(@RequestParam("path") String path, @RequestParam("tag") String tag) {
        TransferDb transferDb = new TransferDb(asyncTaskExecutor, new LineMsProducer(path), lineMsItemRepository, transferElasticSearch);
        asyncTaskExecutor.execute(() -> transferDb.start(tag));
    }
}
