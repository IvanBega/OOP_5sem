package me.bega.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;


public class ThreadPool implements Executor, AutoCloseable {
    private final Queue<Runnable> tasksQueue;
    private final List<Thread> threads;
    private boolean isClosed;

    private void startThreads(int threadCount) {
        for (int i = 0; i < threadCount; i++) {
            var thread = new Thread(() -> {
                while (!isClosed || tasksQueue.size() > 0) {
                    Runnable nextTask = tasksQueue.poll();
                    if (nextTask != null) {
                        nextTask.run();
                    }
                }
            });

            threads.add(thread);
            thread.start();
        }
    }

    public ThreadPool(int threadCount) {
        tasksQueue = new ConcurrentLinkedQueue<>();
        threads = new ArrayList<>(threadCount);
        isClosed = false;

        startThreads(threadCount);
    }

    @Override
    public void execute(Runnable command) {
        if (!isClosed) {
            tasksQueue.offer(command);
        }
    }

    @Override
    public void close() throws InterruptedException {
        isClosed = true;

        for (Thread thread: threads) {
            thread.join();
        }
    }
}