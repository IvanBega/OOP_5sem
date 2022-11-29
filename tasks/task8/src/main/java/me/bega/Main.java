package me.bega;

import me.bega.reentrantlock.CustomReentrantLock;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");
        CustomReentrantLock lock = new CustomReentrantLock();
        Thread t1 = new MyThread(lock);
        Thread t2 = new MyThread(lock);
        t2.start();
        t1.start();
    }
}
