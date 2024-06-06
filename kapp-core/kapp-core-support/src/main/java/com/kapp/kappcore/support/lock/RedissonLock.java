package com.kapp.kappcore.support.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedissonLock implements KappLock {

    private final RedissonClient redissonClient;

    public RedissonLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean lock(String key) {
        RLock lock = redissonClient.getLock(key);
        return lock.tryLock();
    }

    @Override
    public boolean lock(String key, TimeUnit unit, long timeout) {
        RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(timeout, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public boolean unlock(String key) {
        RLock lock = redissonClient.getLock(key);
        try {
            lock.unlock();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
