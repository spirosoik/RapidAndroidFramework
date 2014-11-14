package com.soi.rapidandroidapp.managers;

import android.test.InstrumentationTestCase;

import com.soi.rapidandroidapp.BuildConfig;
import com.soi.rapidandroidapp.models.common.Environment;
import com.soi.rapidandroidapp.utilities.Constants;

/**
 * Created by Spiros I. Oikonomakis on 11/4/14.
 */
public class EnviromentManagerTest extends InstrumentationTestCase {

    public void testEnviroment()
    {
        Environment environment = EnvironmentManager.getInstance().getEnvironment();
        assertNotNull(environment);
        assertEquals(environment.toString(), BuildConfig.ENVIRONMENT);
    }

    public void testApiEnvironment()
    {
        String apiUrl = EnvironmentManager.getInstance().getEnviromentApiUrl();
        assertNotNull(apiUrl);

        switch (EnvironmentManager.getInstance().getEnvironment()) {
            case LIVE:
                assertEquals(apiUrl, Constants.API_LIVE_URL);
                break;
            case STAGING:
                assertEquals(apiUrl, Constants.API_STAGING_URL);
                break;
            case UAT:
                assertEquals(apiUrl, Constants.API_STAGING_URL);
                break;
        }
    }
}
