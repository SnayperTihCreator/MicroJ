package org.tihrc.microj;

import org.junit.jupiter.api.Test;
import org.tihrc.microj.units.SmartInt;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FutureTest {
    @Test
    void powBigInteger() {
        assertEquals("1024", SmartInt.from(2).pow(SmartInt.from(10)).toString());
        assertEquals("1267650600228229401496703205376",
                SmartInt.from(2).pow(SmartInt.from(100)).toString());
        assertEquals("-8",   SmartInt.from(-2).pow(SmartInt.from(3)).toString());
        assertEquals("1",    SmartInt.from(2).pow(SmartInt.from(0)).toString());
        assertEquals("1",    SmartInt.from(-1).pow(SmartInt.from(100000)).toString());
    }
}
