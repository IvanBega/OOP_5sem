package me.bega;

public class Main {
    static MichaelScottQueue<Integer> queue = new MichaelScottQueue<Integer>();
    public static void main(String[] args) throws InterruptedException {
        test();
        for (int i = 0; i < 2000; i++) {
            System.out.println(queue.dequeue());
        }
    }

    private static void test() throws InterruptedException {
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
    }
}
