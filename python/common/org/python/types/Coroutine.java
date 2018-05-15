package org.python.types;

public class Coroutine extends org.python.types.Object {
    public org.python.types.Generator gen;

    private static boolean _DEBUG = false;

    public Coroutine(
        java.lang.String name,
        java.lang.reflect.Method expression,
        java.util.Map<java.lang.String, org.python.Object> stack
    ) {
        this.gen = new Generator(name, expression, stack);
    }

}
