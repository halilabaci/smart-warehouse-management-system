package com.smartwarehouse.datastructure;

import java.util.Objects;

public class CustomHashTable<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private final Entry<K, V>[] table;

    @SuppressWarnings("unchecked")
    public CustomHashTable() {
        this.table = new Entry[DEFAULT_CAPACITY];
    }

    public void put(K key, V value) {
        int index = indexFor(key);
        Entry<K, V> current = table[index];

        if (current == null) {
            table[index] = new Entry<>(key, value);
            return;
        }

        Entry<K, V> previous = null;
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                current.value = value;
                return;
            }
            previous = current;
            current = current.next;
        }
        previous.next = new Entry<>(key, value);
    }

    public V get(K key) {
        int index = indexFor(key);
        Entry<K, V> current = table[index];
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    private int indexFor(K key) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode()) % table.length;
    }

    private static final class Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> next;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
