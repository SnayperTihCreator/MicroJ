package org.tihrc.microj.units;

import java.util.*;
import java.util.AbstractMap;

public class FastMap<V> implements Map<String, V> {
    private String[] keys;
    private V[] values;
    private int size = 0;
    private int capacity;

    @SuppressWarnings({"rawtypes"})
    private static final FastMap EMPTY = new FastMap();

    @SuppressWarnings("unchecked")
    public static <T> FastMap<T> empty() {
        return (FastMap<T>) EMPTY;
    }

    @SuppressWarnings("unchecked")
    public FastMap(){
        this.capacity = 4;
        this.keys = new String[this.capacity];
        this.values = (V[])new Object[this.capacity];
    }

    public static <T> FastMap<T> from(String[] names, T[] values){
        FastMap<T> result = new FastMap<>();
        for (int i = 0; i < names.length; i++)
            result.put(names[i], values[i]);
        return result;
    }

    private int hash(String key){
        return key.hashCode() & (capacity - 1);
    }

    public V get(String key){
        int i = hash(key);
        while (keys[i] != null) {
            if (keys[i].equals(key)) return values[i];
            i = (i + 1) & (capacity - 1);
        }
        return null;
    }

    @Override
    public V get(Object key){
        if (!(key instanceof String)) return null;
        return get((String) key);
    }

    @Override
    public V put(String key, V value) {
        if (this == EMPTY) {
            throw new UnsupportedOperationException("Cannot modify empty FastMap");
        }

        int i = hash(key);
        while (keys[i] != null) {
            if (keys[i].equals(key)) {
                V old = values[i];
                values[i] = value;
                return old;
            }
            i = (i + 1) & (capacity - 1);
        }

        keys[i] = key;
        values[i] = value;
        size++;

        if (size > capacity * 0.7) {
            resize();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        String[] oldKeys = keys;
        V[] oldValues = values;
        capacity *= 2;
        keys = new String[capacity];
        values = (V[]) new Object[capacity];
        size = 0;
        for (int j = 0; j < oldKeys.length; j++) {
            if (oldKeys[j] != null) {
                put(oldKeys[j], oldValues[j]);
            }
        }
    }

    // Ускоренный метод
    public boolean containsKey(String key) {
        int i = hash(key);
        while (keys[i] != null) {
            if (keys[i].equals(key)) return true;
            i = (i + 1) & (capacity - 1);
        }
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        if (!(key instanceof String)) return false;
        return containsKey((String) key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) {
                if (Objects.equals(values[i], value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V remove(Object key) {
        if (this == EMPTY) throw new UnsupportedOperationException("Cannot modify empty FastMap");
        if (!(key instanceof String k)) return null;

        int i = hash(k);
        while (keys[i] != null) {
            if (keys[i].equals(k)) {
                V old = values[i];
                keys[i] = null;
                values[i] = null;
                size--;

                // При удалении в открытой адресации нужно пересчитать хэши следующих элементов
                int j = (i + 1) & (capacity - 1);
                while (keys[j] != null) {
                    String rehashKey = keys[j];
                    V rehashVal = values[j];
                    keys[j] = null;
                    values[j] = null;
                    size--;
                    put(rehashKey, rehashVal);
                    j = (j + 1) & (capacity - 1);
                }
                return old;
            }
            i = (i + 1) & (capacity - 1);
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> m) {
        for (Map.Entry<? extends String, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        if (this == EMPTY) return;
        Arrays.fill(keys, null);
        size = 0;
    }

    @Override
    public Set<String> keySet() {
        Set<String> set = new HashSet<>(size);
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) set.add(keys[i]);
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        List<V> list = new ArrayList<>(size);
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) list.add(values[i]);
        }
        return list;
    }

    @Override
    public Set<Entry<String, V>> entrySet() {
        Set<Entry<String, V>> set = new HashSet<>(size);
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) {
                set.add(new AbstractMap.SimpleEntry<>(keys[i], values[i]));
            }
        }
        return set;
    }

    @Override public int size() { return size; }
    @Override public boolean isEmpty() { return size == 0; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) {
                if (!first) sb.append(", ");
                sb.append(keys[i]).append("=").append(values[i]);
                first = false;
            }
        }
        sb.append("}");
        return sb.toString();
    }
}