package com.soi.rapidandroidapp;

import com.soi.rapidandroidapp.modules.RootTestModule;

import org.robolectric.Robolectric;
import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

/**
 * Created by Spiros I. Oikonomakis on 5/22/15.
 */
public class TestBaseApplication extends BaseApplication implements TestLifecycleApplication {

    @Override
    public void onCreate()
    {
        isRunningTests = true;
        super.onCreate();
    }

    @Override
    public void beforeTest(Method method) {

    }

    @Override
    public void prepareTest(Object test) {
    }

    @Override
    public void afterTest(Method method) {

    }

    @Override
    public Object getRootModule() {
        return new RootTestModule();
    }

    public static  void injectMocks(Object object) {
        TestBaseApplication app = (TestBaseApplication) Robolectric.application;
        app.inject(object);
    }
}