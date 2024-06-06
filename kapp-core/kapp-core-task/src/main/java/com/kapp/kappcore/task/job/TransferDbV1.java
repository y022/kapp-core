package com.kapp.kappcore.task.job;

import com.kapp.kappcore.service.domain.repository.LineMsItemRepository;
import com.kapp.kappcore.task.support.produce.LineMsProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.util.StopWatch;

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

//
//        stopWatch.start("write");
//
//        List<ExecuteItem> items = lineMsProducer.produce(-1);
//
//        List<List<ExecuteItem>> coll = ListUtil.split(items, 1000);
//
//        CountDownLatch cdl = new CountDownLatch(coll.size());
//
//        for (List<ExecuteItem> executeItems : coll) {
//            asyncTaskExecutor.execute(() -> {
//                ArrayList<LineMsItem> m = new ArrayList<>();
//                executeItems.forEach(v -> m.add((LineMsItem) v));
//                try {
//                    executePoint.execute(m);
//                    transferElasticSearch.transfer(m);
//                } catch (Exception e) {
//                    log.error("error:", e);
//                } finally {
//                    cdl.countDown();
//                }
//            });
//        }
//
//        try {
//            cdl.await();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        stopWatch.stop();
//
//        log.info("数据插入完毕,插入耗时:" + stopWatch.getLastTaskTimeMillis());
//        log.info("任务总耗时:" + stopWatch.getTotalTimeMillis());
//        log.info(stopWatch.prettyPrint());
    }

}
