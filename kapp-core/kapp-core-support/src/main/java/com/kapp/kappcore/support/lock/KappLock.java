package com.kapp.kappcore.support.lock;

import java.util.concurrent.TimeUnit;

public interface KappLock {
    boolean lock(String key);
    boolean lock(String key, TimeUnit unit, long timeout);
    boolean unlock(String key);
}
