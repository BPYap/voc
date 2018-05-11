package org.python.types;

public class Coroutine extends org.python.types.Object {
    public org.python.types.Function func;
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




}
