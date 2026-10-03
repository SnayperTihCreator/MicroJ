package org.tihrc.microj;

public class Bridge {
    public static int narrow(int x) { return x; }
    public static String boom() { throw new IllegalArgumentException("bad arg"); }
    public static String show(String s) { return s == null ? "null-ok" : s; }
}
