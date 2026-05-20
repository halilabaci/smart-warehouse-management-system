package com.smartwarehouse.datastructure;

import java.util.List;

public class CustomQueue<T> {

    private final CustomLinkedList<T> elements = new CustomLinkedList<>();

    public void enqueue(T value) {
        elements.add(value);
    }

    public T dequeue() {
        return elements.removeFirst();
    }

    public T peek() {
        if (elements.isEmpty()) {
            return null;
        }
        return elements.get(0);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int size() {
        return elements.size();
    }

    public List<T> toList() {
        return elements.toList();
    }
}
