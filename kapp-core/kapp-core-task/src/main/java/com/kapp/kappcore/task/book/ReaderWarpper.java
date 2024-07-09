package com.kapp.kappcore.task.book;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.dto.book.BookMeta;
import com.kapp.kappcore.model.dto.book.BookType;
import com.kapp.kappcore.model.exception.BizException;
import com.kapp.kappcore.support.mq.MqProducer;
import com.kapp.kappcore.support.mq.MqRouteMapping;
import com.kapp.kappcore.task.book.reader.KappBookReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.List;

/**
 * Author:Heping
 * Date: 2024/7/9 18:04
 */
@Slf4j
@Component
public class ReaderWarpper implements KappBookSaveService {
    private final MqProducer mqProducer;
    private final List<KappBookReader> readers;
    private final AsyncTaskExecutor asyncTaskExecutor;

    public ReaderWarpper(MqProducer mqProducer, List<KappBookReader> readers, AsyncTaskExecutor asyncTaskExecutor) {
        this.mqProducer = mqProducer;
        this.readers = readers;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    @Override
    public void save(String path,BookMeta bookMeta) {
        if (StringUtils.isEmpty(path)) {
            throw new BizException(ExCode.DATE_BLANK, "path is blank!");
        }
        String format = StringUtils.substringAfterLast(path, ".");
        BookType bookType = BookType.typeOf(format);
        if (bookType == null) {
            throw new BizException(ExCode.error, "unsupported format:" + format);
        }
        for (KappBookReader reader : readers) {
            if (reader.support(bookType)) {
                asyncTaskExecutor.execute(() ->
                        reader.read(Paths.get(path), bookMeta, (book -> mqProducer.send(MqRouteMapping.Exchange.BOOK, MqRouteMapping.RoutingKey.SAVE_BOOK, book))));
                log.info("async save task start,path:{}", path);
                break;
            }
        }
    }
}
