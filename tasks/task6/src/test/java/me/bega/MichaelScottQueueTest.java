package me.bega;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MichaelScottQueueTest {
    private MichaelScottQueue<Integer> queue;

    @BeforeEach
    void initializeQueue() {
        queue = new MichaelScottQueue<Integer>();
    }

    @Test
    void testEnqueueAndDequeueOneElement() {
        queue.enqueue(1);
        int actual = queue.dequeue();
        Assertions.assertEquals(1, actual);
    }

    @Test
    void testEnqueueElementsConcurrently() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                queue.enqueue(i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 1000; i < 2000; i++) {
                queue.enqueue(i);
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            result.add(queue.dequeue());
        }

        for (int i = 0; i < 2000; i++) {
            Assertions.assertTrue(result.contains(i));
        }
    }

    @Test
    void testDequeueElementsConcurrently() throws InterruptedException {
        // add 21 - remove 20
        for (int i = 0; i < 21; i++) {
            queue.enqueue(i);
        }
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.dequeue();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.dequeue();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        int last = queue.dequeue();
        Assertions.assertNull(queue.dequeue());
        Assertions.assertTrue(last >=0 && last < 21);
    }
}
