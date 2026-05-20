package com.smartwarehouse.datastructure;

public class CustomStack<T> {

    private final CustomLinkedList<T> elements = new CustomLinkedList<>();

    public void push(T value) {
        elements.addFirst(value);
    }

    public T pop() {
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
}
