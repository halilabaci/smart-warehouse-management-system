package com.smartwarehouse.datastructure;

import java.util.ArrayList;
import java.util.List;

public class CustomPriorityQueue<T extends Comparable<T>> {

    private final List<T> heap = new ArrayList<>();

    public void insert(T item) {
        heap.add(item);
        heapifyUp(heap.size() - 1);
    }

    public T extractMax() {
        if (heap.isEmpty()) {
            return null;
        }
        T max = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        return max;
    }

    public T peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    public List<T> toList() {
        return new ArrayList<>(heap);
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap.get(parent).compareTo(heap.get(index)) >= 0) {
                return;
            }
            swap(parent, index);
            index = parent;
        }
    }

    private void heapifyDown(int index) {
        while (true) {
            int left = (index * 2) + 1;
            int right = (index * 2) + 2;
            int largest = index;

            if (left < heap.size() && heap.get(left).compareTo(heap.get(largest)) > 0) {
                largest = left;
            }
            if (right < heap.size() && heap.get(right).compareTo(heap.get(largest)) > 0) {
                largest = right;
            }
            if (largest == index) {
                return;
            }
            swap(index, largest);
            index = largest;
        }
    }

    private void swap(int first, int second) {
        T temp = heap.get(first);
        heap.set(first, heap.get(second));
        heap.set(second, temp);
    }
}
