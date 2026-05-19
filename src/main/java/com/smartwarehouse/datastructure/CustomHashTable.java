package com.smartwarehouse.datastructure;

public class CustomHashTable<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private final Entry<K, V>[] table;

    @SuppressWarnings("unchecked")
    public CustomHashTable() {
        this.table = new Entry[DEFAULT_CAPACITY];
    }

    public void put(K key, V value) {
        int index = indexFor(key);
        table[index] = new Entry<>(key, value);
    }

    public V get(K key) {
        int index = indexFor(key);
        Entry<K, V> entry = table[index];
        if (entry != null && entry.key.equals(key)) {
            return entry.value;
        }
        return null;
    }

    private int indexFor(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    private static final class Entry<K, V> {
        private final K key;
        private final V value;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
