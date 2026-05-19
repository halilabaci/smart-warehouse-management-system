package com.smartwarehouse.datastructure;

public class CustomQueue<T> {

    private final CustomLinkedList<T> elements = new CustomLinkedList<>();

    public void enqueue(T value) {
        elements.add(value);
    }

    public T dequeue() {
        throw new UnsupportedOperationException("Dequeue implementation will be added in later iterations.");
    }

    public boolean isEmpty() {
        return elements.size() == 0;
    }
}
