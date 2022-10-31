package me.bega;

public class Main {
    public static void main(String[] args) {
        MichaelScottQueue<Integer> queue = new MichaelScottQueue<Integer>();
        queue.enqueue(5);
        System.out.println(queue.dequeue());
    }
}
