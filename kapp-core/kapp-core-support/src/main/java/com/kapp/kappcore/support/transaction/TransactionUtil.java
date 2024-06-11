package com.kapp.kappcore.support.transaction;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Author:Heping
 * Date: 2024/6/6 18:05
 */
public class TransactionUtil {
    /**
     * 在事务commit之后，注册一个callback
     * @param runnable 回调执行的任务
     */
    public static void committedCallback(Runnable runnable) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    runnable.run();
                }
            });
        }
    }

}

