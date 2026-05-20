package com.smartwarehouse.datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomHashTable<K, V> {

    private static final int DEFAULT_CAPACITY = 32;
    private final Entry<K, V>[] table;

    @SuppressWarnings("unchecked")
    public CustomHashTable() {
        this.table = new Entry[DEFAULT_CAPACITY];
    }

    public int hashFunction(K key) {
        if (key == null) {
            return 0;
        }
        return Math.floorMod(key.hashCode(), table.length);
    }

    public void put(K key, V value) {
        int index = hashFunction(key);
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
        Entry<K, V> current = table[hashFunction(key)];
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public V remove(K key) {
        int index = hashFunction(key);
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    public List<V> values() {
        List<V> values = new ArrayList<>();
        for (Entry<K, V> bucket : table) {
            Entry<K, V> current = bucket;
            while (current != null) {
                values.add(current.value);
                current = current.next;
            }
        }
        return values;
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
