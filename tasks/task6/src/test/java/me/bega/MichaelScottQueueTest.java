package me.bega;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
