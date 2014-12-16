package com.soi.rapidandroidapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.activeandroid.ActiveAndroid;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(RobolectricTestRunner.class)
public class ApplicationTest extends ApplicationTestCase<BaseApplication> {


    public ApplicationTest() {
        super(BaseApplication.class);
    }
}