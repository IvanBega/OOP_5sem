package me.bega;

import java.util.concurrent.atomic.AtomicReference;

public class Node<T> {
    public T data;
    public AtomicReference<Node<T>> next = new AtomicReference<>();

    public Node(T data) {
        this.data = data;
    }

    public Node() {}
}