package org.tihrc.microj.units;

import java.util.List;

public final class LineTables {
    private LineTables() {}

    public static int[] build(int size, List<int[]> markers) {
        int[] t = new int[size];
        int mi = 0, cur = markers.isEmpty() ? 0 : markers.getFirst()[1];
        for (int pc = 0; pc < size; pc++) {
            while (mi < markers.size() && markers.get(mi)[0] <= pc) cur = markers.get(mi++)[1];
            t[pc] = cur;
        }
        return t;
    }
}
