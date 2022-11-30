package me.bega.threadpool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ThreadPoolTest {
    private ThreadPool threadPool1;
    private ThreadPool threadPool2;
    private long time1;
    private long time2;

    @BeforeEach
    void setup() {
        threadPool1 = new ThreadPool(2);
        threadPool2 = new ThreadPool(1);
    }
    Runnable getThread() {
        return new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
    @Test
    void testThreadPool() throws InterruptedException {
        time1 = System.currentTimeMillis();
        threadPool1.execute(getThread());
        threadPool1.execute(getThread());
        threadPool1.close();
        time1 = System.currentTimeMillis() - time1;

        time2 = System.currentTimeMillis();
        threadPool2.execute(getThread());
        threadPool2.execute(getThread());
        threadPool2.close();
        time2 = System.currentTimeMillis() - time2;

        Assertions.assertTrue(time1 < time2);
    }
}
