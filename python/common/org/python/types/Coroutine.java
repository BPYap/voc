package org.python.types;

public class Coroutine extends org.python.types.Generator {

    private static boolean _DEBUG = false;

    /**
     * Implements decorator @asyncio.coroutine
     */
    public Coroutine(
            java.lang.String name,
            java.lang.reflect.Method expression,
            java.util.Map<java.lang.String, org.python.Object> stack
    ) {
        super(name, expression, stack);
    }

    /**
     * Called when coroutine returns
     */
    public void finish() {

    }

    /**
     * Implements iscoroutinefunctione
     */
    public static org.python.types.Bool iscoroutinefunction(org.python.types.Object obj) {
        org.python.types.Function func;
        if (obj instanceof org.python.types.Function) {
            func = (org.python.types.Function) obj;
        } else if (obj instanceof org.python.types.Method) {
            func = ((org.python.types.Method) obj).im_func;
        } else {
            return new org.python.types.Bool(false);
        }
        return new org.python.types.Bool(((func.code.co_flags.value & Function.CO_COROUTINE) > 0));
    }

    /**
     * Implements asyncio.iscoroutine
     */
    public static org.python.types.Bool iscoroutine(org.python.types.Object obj) {
        return new org.python.types.Bool(obj instanceof org.python.types.Coroutine);
    }

}
