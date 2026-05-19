package com.smartwarehouse.datastructure;

public class CustomStack<T> {

    private final CustomLinkedList<T> elements = new CustomLinkedList<>();

    public void push(T value) {
        elements.add(value);
    }

    public T pop() {
        throw new UnsupportedOperationException("Pop implementation will be added in later iterations.");
    }

    public boolean isEmpty() {
        return elements.size() == 0;
    }
}
