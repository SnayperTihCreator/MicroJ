package org.tihrc.microj;

import org.junit.jupiter.api.Test;
import org.tihrc.microj.core.exceptions.PyUnwind;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.types.primitives.PyBool;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.types.primitives.PyString;
import org.tihrc.microj.units.SmartInt;

import java.math.BigInteger;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class TransformsTest {
    private static PyInt bigPow2(int exp) {
        return PyInt.from(Objects.requireNonNull(new SmartInt(2).pow(new SmartInt(exp))));
    }

    @Test void fromPythonLong() {
        assertEquals(5L, Transforms.fromPython(PyInt.from(5), Long.class));
        assertEquals(5L, Transforms.fromPython(PyInt.from(5), long.class));
    }

    @Test void fromPythonOverflowGuard() {
        assertThrows(PyUnwind.class,
                () -> Transforms.fromPython(bigPow2(100), Integer.class));
        assertThrows(PyUnwind.class,
                () -> Transforms.fromPython(bigPow2(100), Long.class));
        assertDoesNotThrow(() -> Transforms.fromPython(bigPow2(62), Long.class));
    }

    @Test void fromPythonBigIntegerNoLoss() {
        assertEquals(new BigInteger("1267650600228229401496703205376"),
                Transforms.fromPython(bigPow2(100), BigInteger.class));
    }

    @Test void strictVsSoft() {
        assertNull(Transforms.fromPythonOrNull(new PyString("s"), Integer.class));
        assertThrows(PyUnwind.class,
                () -> Transforms.fromPython(new PyString("s"), Integer.class));
    }

    @Test void toPythonRoundTrip() {
        assertEquals(PyInt.from(5), Transforms.toPython(5));
        assertEquals(PyInt.from(5L), Transforms.toPython(5L));
        assertEquals(new PyString("s"), Transforms.toPython("s"));
        assertEquals(new PyString("c"), Transforms.toPython('c'));
        assertEquals(PyBool.TRUE, Transforms.toPython(true));
    }
}