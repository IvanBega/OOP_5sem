package me.bega.cyclicbarrier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;

public class CyclicBarrierTest {
    private CyclicBarrierInterface cyclicBarrier;
    private boolean finished = false;
    @BeforeEach
    public void setup() {
        cyclicBarrier = new CyclicBarrier(3, () -> finished = true);
    }
    private void makeThread() {
        Thread thread = new Thread(() ->{
            try {
                cyclicBarrier.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
    @Test
    public void testAllThreadsReachBarrier() throws InterruptedException {
        makeThread();
        makeThread();
        makeThread();
        synchronized (this) {
            this.wait(1000);
        }
        Assertions.assertTrue(finished);
    }
    @Test
    public void testOneThreadRemainingToReachBarrier() throws InterruptedException {
        makeThread();
        makeThread();
        synchronized (this) {
            this.wait(2000);
        }
        Assertions.assertEquals(1, cyclicBarrier.getNumberWaiting());
    }
    @Test
    public void testBarrierRelease() throws InterruptedException {
        makeThread();
        makeThread();
        makeThread();
        synchronized (this) {
            this.wait(1000);
        }
        Assertions.assertTrue(finished);
        Assertions.assertEquals(3, cyclicBarrier.getNumberWaiting());
    }

}
