package org.tihrc.microj.core;

import java.util.Map;

public enum Capability {
    NONE(),
    NUMBER("__add__", "__sub__", "__mul__", "__truediv__", "__neg__"),
    CONTAINER("__getitem__", "__setitem__", "__len__", "__contains__"),
    COMPARABLE("__eq__", "__ne__", "__lt__", "__le__", "__gt__", "__ge__"),
    ITERABLE("__iter__"),
    CALLABLE("__call__"),
    ITERATOR("__next__");

    private final int bit;
    private final String[] dunders;

    Capability(String... dunders) {
        this.dunders = dunders;
        this.bit = 1 << this.ordinal();
    }

    public int bit() {
        return this.bit;
    }

    public <T> boolean matches(Map<String, T> map){
        for (String dunder: dunders)
            if (map.containsKey(dunder)) return true;
        return false;
    }

    public static int combine(Capability... caps){
        int result = 0;
        for (Capability c : caps) {
            result |= c.bit();
        }
        return result;
    }
}
