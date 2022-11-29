package me.bega;

import me.bega.reentrantlock.CustomReentrantLock;

public class MyThread extends Thread{
    private CustomReentrantLock lock;
    public MyThread(CustomReentrantLock lock) {
        this.lock = lock;
    }
    @Override
    public void run() {
        System.out.println("Thread is waiting for lock");
        lock.lock();
        System.out.println("Thread acquired lock");
        try {
            Thread.sleep(2000);
            System.out.println("Thread is sleeping");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock.unlock();
        System.out.println("Thread released lock");
    }
}
