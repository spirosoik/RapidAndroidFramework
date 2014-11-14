package com.soi.rapidandroidapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.activeandroid.ActiveAndroid;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {


    public ApplicationTest() {
        super(Application.class);
    }


    public ApplicationTest(Class<Application> applicationClass) {
        super(applicationClass);
    }
}