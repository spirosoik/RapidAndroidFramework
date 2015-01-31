package com.soi.rapidandroidapp.modules;

import dagger.ObjectGraph;

/**
 * Created by spirosoikonomakis on 3/9/14.
 */
public class Injector {

    public static ObjectGraph objectGraph;

    public static void init(Object rootModule) {
        if (objectGraph == null) {
            objectGraph = ObjectGraph.create(rootModule);
        } else {
            objectGraph = objectGraph.plus(rootModule);
        }

        objectGraph.injectStatics();
    }

    public static void init(final Object rootModule, final Object target) {
        init(rootModule);
        inject(target);
    }

    public static void inject(final Object target) {
        objectGraph.inject(target);
    }

    public static <T> T resolve(Class<T> type) {
        return objectGraph.get(type);
    }
}