package com.soi.rapidandroidapp;

import android.test.ApplicationTestCase;

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