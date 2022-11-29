package me.bega.reentrantlock;

public interface ReentrantLock {
    void lock();
    boolean tryLock();
    void unlock();
}
