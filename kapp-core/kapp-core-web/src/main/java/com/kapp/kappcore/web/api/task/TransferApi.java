package com.kapp.kappcore.web.api.task;

import com.kapp.kappcore.service.domain.repository.LineMsItemRepository;
import com.kapp.kappcore.task.job.TransferDbV1;
import com.kapp.kappcore.task.job.TransferDbV2;
import com.kapp.kappcore.task.job.TransferElasticSearch;
import com.kapp.kappcore.task.support.produce.LineMsProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
        TransferDbV1 transferDbV1 = new TransferDbV1(asyncTaskExecutor, new LineMsProducer(path), lineMsItemRepository, transferElasticSearch);
        asyncTaskExecutor.execute(() -> transferDbV1.transfer(tag));
    }

    @GetMapping("/bookV2")
    public void transferV2(@RequestParam("path") String path, @RequestParam("tag") String tag) {
        TransferDbV2 transferDbV2 = new TransferDbV2(asyncTaskExecutor, new LineMsProducer(path), lineMsItemRepository, transferElasticSearch);
        asyncTaskExecutor.execute(() -> transferDbV2.transfer(tag));
    }
}
