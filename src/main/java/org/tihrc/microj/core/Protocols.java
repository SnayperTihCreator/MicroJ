package org.tihrc.microj.core;

import org.tihrc.microj.units.FastMap;

import java.util.Map;
import java.util.function.Consumer;

public class Protocols {

    public interface PyCallable {
        PyObject pyDanderCall(RuntimeExecuter ctx, Map<String, PyObject> kwargs, PyObject... args);
        default void pyDanderCall(RuntimeExecuter ctx, Consumer<PyObject> callback, PyObject[] args, Map<String, PyObject> kwargs){}
        default PyObject pyDanderCallBound(RuntimeExecuter ctx, PyObject self, Map<String, PyObject> kwargs, PyObject[] args){
            PyObject[] fullArgs = new PyObject[args.length+1];
            fullArgs[0] = self;
            System.arraycopy(args, 0, fullArgs, 1, args.length);
            return pyDanderCall(ctx, kwargs, fullArgs);
        }
        default PyObject pyDanderCallBoundFast(RuntimeExecuter ctx, PyObject self, PyObject[] args, String[] kwNames, PyObject[] kwValues){
            PyObject[] fullArgs = new PyObject[args.length+1];
            fullArgs[0] = self;
            System.arraycopy(args, 0, fullArgs, 1, args.length);
            return pyDanderCallFast(ctx, fullArgs, kwNames, kwValues);
        }
        default PyObject pyDanderCallFast(RuntimeExecuter ctx, PyObject[] args, String[] kwNames, PyObject[] kwValue){
            Map<String, PyObject> kwargs = FastMap.empty();

            if (kwNames != null) {
                FastMap<PyObject> map = new FastMap<>();
                for (int i = 0; i < kwNames.length; i++)
                    map.put(kwNames[i], kwValue[i]);
                kwargs = map;
            }
            return pyDanderCall(ctx, kwargs, args);
        }
        default PyObject pyDanderCallFast(RuntimeExecuter ctx) {
            return pyDanderCallFast(ctx, new PyObject[0], null, null);
        }
    }

    public interface PyComparable {
        boolean pyDanderBool();          // Истинность объекта (if object:)

        PyObject pyDanderEq(PyObject other);  // ==
        PyObject pyDanderNe(PyObject other);  // !=
        PyObject pyDanderLt(PyObject other);  // <
        PyObject pyDanderGt(PyObject other);  // >
        PyObject pyDanderLe(PyObject other);  // <=
        PyObject pyDanderGe(PyObject other);  // >=
    }

    public interface PyIterable {
        PyIterator pyDanderIter();
    }

    public interface PyIterator {
        PyObject pyDanderNext();
    }

    public interface PyNumber {
        PyObject pyDanderAdd(PyObject other);
        PyObject pyDanderSub(PyObject other);
        PyObject pyDanderMul(PyObject other);
        PyObject pyDanderTrueDiv(PyObject other);
        PyObject pyDanderFloorDiv(PyObject other);
        PyObject pyDanderMod(PyObject other);
        PyObject pyDanderNeg();
    }

    public interface PyContainer {
        PyObject pyDanderGetItem(PyObject index);
        void pyDanderSetItem(PyObject index, PyObject value);
        int pyDanderLen();
        PyObject pyDanderContains(PyObject index);
    }
}
