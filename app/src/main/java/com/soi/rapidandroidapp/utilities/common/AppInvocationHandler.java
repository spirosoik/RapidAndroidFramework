package com.soi.rapidandroidapp.utilities.common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Spiros I. Oikonomakis on 12/1/14.
 */
public class AppInvocationHandler implements InvocationHandler {
    public Object o;

    public AppInvocationHandler(Object o) {
        this.o = o;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(o, args);
        } catch ( Exception e ) {
            return null;
        }
    }
}
