package me.bega.reentrantlock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomReentrantLockTest {
    private ReentrantLock lock;
    @BeforeEach
    public void setup() {
        lock = new CustomReentrantLock();
    }

    @Test
    public void testTryLockShouldReturnTrue() throws InterruptedException {
        Thread thread  = new Thread(() -> {
            lock.lock();
            lock.unlock();
        });
        thread.start();
        thread.join();
        Assertions.assertTrue(lock.tryLock());
    }
    @Test
    public void testShouldLockAndRelease() throws InterruptedException {
        Thread thread = new Thread(() -> {
            lock.lock();
            lock.lock();
            lock.lock();
            lock.unlock();
            lock.unlock();
            lock.unlock();
        });
        thread.start();
        thread.join();
        Assertions.assertTrue(lock.tryLock());
        lock.unlock();
    }
    @Test
    public void testTryLockShouldReturnFalse() {
        lock.lock();
        lock.lock();
        lock.unlock();
        Assertions.assertFalse(lock.tryLock());
        lock.unlock();
    }
}
