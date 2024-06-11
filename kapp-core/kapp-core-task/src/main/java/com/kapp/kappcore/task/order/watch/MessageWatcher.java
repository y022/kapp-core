package com.kapp.kappcore.task.order.watch;

import com.kapp.kappcore.model.dto.share.message.KappMessage;

/**
 * Author:Heping
 * Date: 2024/6/6 20:26
 */
public interface MessageWatcher<T extends KappMessage> {
    /**
     * accept and handle message
     *
     * @param t message obj
     * @throws Exception ex
     */
    void watch(T t) throws Exception;

}
