package com.kapp.kappcore.support.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * todo
 */
//@Component
public class LocalLockImpl implements KappLock {

    private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();

    @Override
    public boolean lock(String key) {
        return false;
    }

    @Override
    public boolean lock(String key, TimeUnit unit, long timeout) {
        return false;
    }

    @Override
    public boolean unlock(String key) {
        return false;
    }
}
