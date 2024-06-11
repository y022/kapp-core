package com.kapp.kappcore.task.order.watch;

import com.kapp.kappcore.model.dto.share.message.KappMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;

/**
 * Author:Heping
 * Date: 2024/6/6 20:33
 */
@Slf4j
public abstract class AbstractMessageWatcher<T extends KappMessage> implements MessageWatcher<T> {

    /**
     * message consume template method
     * control a scene and prevent exceptions from being thrown to the upper level
     *
     * @param messageConsumer consumer function
     * @param message         message obj
     */
    protected void processMessage(Consumer<T> messageConsumer, T message) {
        try {
            if (message == null || StringUtils.isBlank(message.messageId())) {
                log.error("message is null or messageId is blank!");
                return;
            }
            log.info("message start consume, messageId: {}", message.messageId());
            messageConsumer.accept(message);
        } catch (Throwable e) {
            log.error("message consume exception!", e);
        }
    }

    protected Consumer<T> defaultConsumer() {
        return (m) -> {
        };
    }
}
