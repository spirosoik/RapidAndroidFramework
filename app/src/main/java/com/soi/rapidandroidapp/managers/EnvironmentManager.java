package com.soi.rapidandroidapp.managers;


import com.soi.rapidandroidapp.BuildConfig;
import com.soi.rapidandroidapp.models.common.Environment;
import com.soi.rapidandroidapp.utilities.Constants;

import retrofit.RestAdapter;

/**
 * Created by Spiros I. Oikonomakis on 11/4/14.
 *
 * This class handles all the available enviroments according
 * to the build types.
 *
 * TODO: maybe use variants also
 */
public class EnvironmentManager {

    private static EnvironmentManager mInstance;

    /**
     * Singleton
     * @return EnvironmentManager
     */
    public static synchronized EnvironmentManager getInstance()
    {
        if ( mInstance == null ) {
            mInstance = new EnvironmentManager();
        }
        return mInstance;
    }

    /**
     * Returns the url of the current build type.
     * eg.
     * if BuildConfig.environment = "debug" => returns staging Environment
     * @return Environment
     */
    public Environment getEnvironment()
    {
        Environment environment = null;
        if (BuildConfig.ENVIRONMENT.equals("STAGING")) {
            environment = Environment.STAGING;
        }  else if (BuildConfig.ENVIRONMENT.equals("LIVE")) {
            environment = Environment.LIVE;
        } else if (BuildConfig.ENVIRONMENT.equals("UAT")) {
            environment = Environment.UAT;
        }
        return environment;
    }

    /**
     * Returns the url of the current build type.
     * eg.
     * if BuildConfig.environment = "debug" => returns staging URL
     * @return String
     */
    public String getEnviromentApiUrl()
    {
        String apiUrl = null;
        if (BuildConfig.ENVIRONMENT.equals("STAGING")) {
            apiUrl = Constants.API_STAGING_URL;
        }  else if (BuildConfig.ENVIRONMENT.equals("LIVE")) {
            apiUrl = Constants.API_LIVE_URL;
        } else if (BuildConfig.ENVIRONMENT.equals("UAT")) {
            apiUrl = Constants.API_UAT_URL;
        }
        return apiUrl;
    }

    /**
     * Returns the API log level according to the current build environment.
     * eg.
     * if BuildConfig.environment = "debug" => returns FULL Logging
     * @return RestAdapter.LogLevel
     */
    public RestAdapter.LogLevel getEnvironmentLogLevel()
    {
        RestAdapter.LogLevel logLevel = null;
        if (BuildConfig.ENVIRONMENT.equals("STAGING")) {
            logLevel = RestAdapter.LogLevel.FULL;
        }  else if (BuildConfig.ENVIRONMENT.equals("LIVE")) {
            logLevel = RestAdapter.LogLevel.NONE;
        } else if (BuildConfig.ENVIRONMENT.equals("UAT")) {
            logLevel = RestAdapter.LogLevel.FULL;
        }
        return logLevel;
    }
}
