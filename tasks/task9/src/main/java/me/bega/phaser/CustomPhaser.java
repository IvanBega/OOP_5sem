package me.bega.phaser;

public class CustomPhaser {
    private int parties;
    private int partiesAwait;
    private int phase;


    int getPhase() {
        return phase;
    }
    public CustomPhaser(int parties) {
        this.parties = parties;
        this.partiesAwait = parties;
        this.phase = 0;
    }
    public CustomPhaser() {
        this(0);
    }

    public synchronized int register() {
        parties++;
        partiesAwait++;
        return phase;
    }

    public synchronized int arrive() {
        partiesAwait--;
        int currentPhase = phase;
        if (partiesAwait == 0) {
            notifyAll();
            partiesAwait = parties;
            phase = currentPhase + 1;
        }

        return phase;
    }

    public int arriveAndAwaitAdvance() {
        int currentPhase = phase;
        synchronized (this) {
            partiesAwait--;

            while (partiesAwait > 0 && partiesAwait != parties) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            partiesAwait = parties;
            phase = currentPhase + 1;
            notifyAll();

            return phase;
        }
    }

    public synchronized int arriveAndDeregister() {
        partiesAwait--;
        parties--;
        int currentPhase = phase;
        if (partiesAwait == 0) {
            partiesAwait = parties;
            phase = currentPhase + 1;
            notifyAll();
        }

        return phase;
    }
    public synchronized int getPartiesAwait() {
        return partiesAwait;
    }
}
