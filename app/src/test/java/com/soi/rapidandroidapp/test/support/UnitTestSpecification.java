package com.soi.rapidandroidapp.test.support;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.logging.LogManager;

/**
 * Created by Spiros I. Oikonomakis on 5/18/15.
 */
@RunWith(value = RobolectricDataBindingTestRunner.class)
public abstract class UnitTestSpecification {

    public UnitTestSpecification() {
        // found no other way to set LogManager configuration by property file
        try {
            if (getClass().getResourceAsStream("/logging.properties") != null) {
                LogManager.getLogManager().readConfiguration(getClass().getResourceAsStream("/logging.properties"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}