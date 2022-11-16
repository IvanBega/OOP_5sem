package me.bega.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;

public interface CyclicBarrierInterface {
    void await()
            throws InterruptedException, BrokenBarrierException;

    void reset();

    boolean isBroken();

    int getParties();

    int getNumberWaiting();
}
