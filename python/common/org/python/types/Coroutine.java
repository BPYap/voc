package org.python.types;

public class Coroutine extends org.python.types.Object {
    public org.python.types.Function func;

    private static boolean _DEBUG = false;

    public Coroutine() {
        func = null;
    }

    /**
     * Constructor for coroutine object defined via "async def" syntax.
     */
    public Coroutine(org.python.types.Function func)
    {
        org.python.types.Int co_flags = func.code.co_flags;

        if ((co_flags.value & (Function.CO_COROUTINE | Function.CO_ITERABLE_COROUTINE)) > 0) {
            // already a coroutine, do nothing
        }
        else if ((co_flags.value & Function.CO_GENERATOR) > 0) {
            // transforms generator into coroutine by modifying co_flags
            org.python.types.Code co = func.code;
            func.code = new org.python.types.Code(
                co.co_argcount,
                co.co_cellvars,
                co.co_code,
                co.co_consts,
                co.co_filename,
                co.co_firstlineno,
                new org.python.types.Int(co.co_flags.value | Function.CO_ITERABLE_COROUTINE),
                co.co_freevars,
                co.co_kwonlyargcount,
                co.co_lnotab,
                co.co_name,
                co.co_names,
                co.co_nlocals,
                co.co_stacksize,
                co.co_varnames
            );
        }
        else {
            // wrap function that returns a coroutine or generator object
            // TODO: code to wrap the function
        }

        this.func = func;
    }

    /**
     * Implementation of coroutine decorator from asyncio.coroutines.py
     * Another way to define coroutine
     * Simply decorates a generator function with "@asyncio.coroutine"
     */
    public static org.python.types.Coroutine coroutine(org.python.types.Function func) {
        org.python.types.Coroutine coro;

        if (_iscoroutinefunction(func)) {
            coro = new Coroutine();
            coro.func = func;
            return coro;
        }

        if (!_isgeneratorfunction(func)) {
            // TODO: code to wrap func to support Awaitable or Future object
        }

        if (!_DEBUG) {
            coro = new Coroutine(func);
        }
        else {
            // TODO: create debug version of coroutine
        }

        coro.__setattr__("_is_coroutine", org.python.types.Bool.TRUE);
        return coro;
    }

    /**
     * Implementation of isgeneratorfunction(object) from inspect.py
     * Return true if the object is a user-defined generator function.
     */
    private static boolean _isgeneratorfunction(org.python.types.Object object) {
        org.python.types.Function func;
        if (object instanceof  org.python.types.Function) {
            func = (org.python.types.Function)object;
        }
        else if (object instanceof org.python.types.Method) {
            func = ((org.python.types.Method)object).im_func;
        }
        else {
            return false;
        }
        return ((func.code.co_flags.value & Function.CO_GENERATOR) > 0);
    }

    /**
     * Implementation of iscoroutinefunction(object) from inspect.py
     * Return true if the object is a coroutine function.
     * Coroutine functions are defined with "async def" syntax.
     */
    private static boolean _iscoroutinefunction(org.python.types.Object object) {
        org.python.types.Function func;
        if (object instanceof  org.python.types.Function) {
            func = (org.python.types.Function)object;
        }
        else if (object instanceof org.python.types.Method) {
            func = ((org.python.types.Method)object).im_func;
        }
        else {
            return false;
        }
        return ((func.code.co_flags.value & Function.CO_COROUTINE) > 0);
    }





}
