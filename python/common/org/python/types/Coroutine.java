package org.python.types;

public class Coroutine extends org.python.types.Generator {
    public Coroutine(
        java.lang.String name,
        java.lang.reflect.Method expression,
        java.util.Map<java.lang.String, org.python.Object> stack
    ) {
        super(name, expression, stack);
    }
}
