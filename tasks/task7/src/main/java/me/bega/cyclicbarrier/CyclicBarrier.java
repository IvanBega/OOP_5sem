package me.bega.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;

public class CyclicBarrier implements CyclicBarrierInterface {
    private final int parties;
    private int numberWaiting;
    private final Runnable barrierEvent;
    private boolean isBroken;

    public CyclicBarrier(int parties, Runnable barrierEvent) {
        this.parties = parties;
        this.numberWaiting = parties;
        this.barrierEvent = barrierEvent;
        this.isBroken = false;
    }

    public CyclicBarrier(int parties) {
        this(parties, null);
    }

    @Override
    public synchronized void await()
            throws InterruptedException, BrokenBarrierException {
        if (this.isBroken) {
            throw new BrokenBarrierException();
        }

        --this.numberWaiting;

        while (this.numberWaiting > 0 && this.numberWaiting != this.parties) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                this.isBroken = true;
                throw e;
            }
        }

        this.reset();
        notifyAll();
        if (this.barrierEvent != null) {
            this.barrierEvent.run();
        }

    }

    @Override
    public void reset() {
        this.numberWaiting = this.parties;
        this.isBroken = false;
    }

    @Override
    public boolean isBroken() {
        return this.isBroken;
    }

    @Override
    public int getParties() {
        return this.parties;
    }

    @Override
    public int getNumberWaiting() {
        return this.numberWaiting;
    }
}
