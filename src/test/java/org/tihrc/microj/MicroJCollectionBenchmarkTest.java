package org.tihrc.microj;

import org.junit.jupiter.api.Test;
import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.types.collections.PyDict;
import org.tihrc.microj.types.collections.PyList;
import org.tihrc.microj.types.collections.PyString;
import org.tihrc.microj.types.collections.PyTuple;
import org.tihrc.microj.types.primitives.PyInt;

public class MicroJCollectionBenchmarkTest extends MicroJBenchmarkBase{
    @Test
    void benchmarkDirectListAccess() {
        PyList list = new PyList(new PyObject[]{
                PyInt.from(1),
                PyInt.from(2),
                PyInt.from(3)
        });

        PyInt index = PyInt.from(1);

        benchmarkJava(
                "Java List getItem",
                () -> list.pyDanderGetItem(index),
                10000,
                1_000_000
        );
    }

    @Test
    void benchmarkDirectTupleAccess() {
        PyTuple tuple = new PyTuple(new PyObject[]{
                PyInt.from(1),
                PyInt.from(2),
                PyInt.from(3)
        });

        PyInt index = PyInt.from(1);

        benchmarkJava(
                "Java Tuple getItem",
                () -> tuple.pyDanderGetItem(index),
                10000,
                1_000_000
        );
    }

    @Test
    void benchmarkDirectDictAccess() {
        PyDict dict = new PyDict();
        PyString key = new PyString("b");

        dict.put(
                new PyString("a"),
                PyInt.from(1)
        );

        dict.put(
                key,
                PyInt.from(2)
        );

        dict.put(
                new PyString("c"),
                PyInt.from(3)
        );

        benchmarkJava(
                "Java Dict getItem",
                () -> dict.pyDanderGetItem(key),
                10000,
                1_000_000
        );
    }

    @Test
    void benchmarkBinarySubscript() {
        String code = """
                a = [1, 2, 3]
                x = a[1]
                y = a[1]
                z = a[1]
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmark(
                "BinarySubscript",
                interpreter,
                script,
                20,
                10000
        );
    }
}
