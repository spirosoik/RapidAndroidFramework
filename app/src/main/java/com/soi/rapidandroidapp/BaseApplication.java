package com.soi.rapidandroidapp;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.soi.rapidandroidapp.modules.Injector;
import com.soi.rapidandroidapp.modules.RootModule;

/**
 * Created by spirosoikonomakis on 3/9/14.
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    public BaseApplication() {

    }

    /**
     * Create main application
     * @param context
     */
    public BaseApplication(final Context context) {
        super();
        attachBaseContext(context);

    }

    /**
     * Create main application
     * @param instrumentation
     */
    public BaseApplication(final Instrumentation instrumentation) {
        super();
        attachBaseContext(instrumentation.getTargetContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);

        instance = this;

        // Perform injection
        Injector.init(getRootModule(), this);

    }

    public static BaseApplication getInstance() {
        return instance;
    }

    private Object getRootModule() {
        return new RootModule();
    }
}