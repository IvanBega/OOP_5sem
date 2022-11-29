package me.bega.phaser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Phaser;

public class CustomPhaserTest {
    CustomPhaser phaser;
    @BeforeEach
    void setup() {
        phaser = new CustomPhaser(2);
    }

    void arrive() {
        Thread thread = new Thread(() -> {
            phaser.arrive();
        });
        thread.start();
    }
    void arriveThreadAndWaitAdvance() {
        Thread thread = new Thread(() -> {
            phaser.arriveAndAwaitAdvance();
        });
        thread.start();
    }
    void arriveThreadAndDeregister() {
        Thread thread = new Thread(() -> {
            phaser.arriveAndDeregister();
        });
        thread.start();
    }
    @Test
    void testShouldReachPhaseTwo() throws InterruptedException {
        arriveThreadAndWaitAdvance();
        arriveThreadAndWaitAdvance();
        synchronized (this) {
            wait(1000);
        }
        arriveThreadAndWaitAdvance();
        arriveThreadAndWaitAdvance();
        synchronized (this) {
            wait(1000);
        }

        Assertions.assertEquals(2, phaser.getPhase());
    }
    @Test
    void testAwaitShouldReachPhaseOne() throws InterruptedException {
        arriveThreadAndWaitAdvance();
        synchronized (this) {
            wait(1000);
        }
        arrive();
        synchronized (this) {
            wait(1000);
        }

        Assertions.assertEquals(1, phaser.getPhase());
    }
    @Test
    void testShouldReachPhaseWithDeregistration() throws InterruptedException {
        arrive();
        arriveThreadAndDeregister();
        synchronized (this) {
            wait(1000);
        }
        Assertions.assertEquals(1, phaser.getPhase());
    }
    @Test
    void testShouldReachPhaseWithRegistration() throws InterruptedException {
        Thread thread = new Thread(() -> {
            phaser.register();
        });
        thread.start();
        thread.join();
        arrive();
        synchronized (this) {
            wait(1000);
        }
        arrive();
        synchronized (this) {
            wait(1000);
        }
        Assertions.assertEquals(0, phaser.getPhase());
        arrive();
        synchronized (this) {
            wait(1000);
        }
        Assertions.assertEquals(1, phaser.getPhase());
    }
}
