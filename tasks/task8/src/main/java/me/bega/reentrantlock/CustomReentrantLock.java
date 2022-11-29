package me.bega.reentrantlock;

public class CustomReentrantLock {
    private int count;
    private long currentThreadID;

    public CustomReentrantLock() {
        this.count = 0;
    }

    public synchronized void lock() {
        if (count == 0) {
            count++;
            currentThreadID = Thread.currentThread().getId();
        } else if (currentThreadID != Thread.currentThread().getId()) {
            while (currentThreadID != Thread.currentThread().getId()) {
                try {
                    wait();
                    count++;
                    currentThreadID = Thread.currentThread().getId();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            count++;
        }
    }

    public synchronized void unlock() {
        if(count == 0) {
            throw new IllegalMonitorStateException();
        }

        count--;

        if(count == 0) {
            notify();
        }
    }

    public boolean tryLock() {
        if (count == 0) {
            lock();
            return true;
        }
        return false;
    }
}
