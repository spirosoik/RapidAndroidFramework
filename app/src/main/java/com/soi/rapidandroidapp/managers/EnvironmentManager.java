package com.soi.rapidandroidapp.managers;


import android.util.Log;

import com.soi.rapidandroidapp.BuildConfig;
import com.soi.rapidandroidapp.models.common.Environment;
import com.soi.rapidandroidapp.utilities.Constants;

import hugo.weaving.DebugLog;
import retrofit.RestAdapter;

/**
 * Created by Spiros I. Oikonomakis on 11/4/14.
 * <p/>
 * This class handles all the available enviroments according
 * to the build types.
 * <p/>
 * TODO: maybe use variants also
 */
public class EnvironmentManager {

    private static EnvironmentManager mInstance;

    /**
     * Singleton
     *
     * @return EnvironmentManager
     */
    @DebugLog
    public static synchronized EnvironmentManager getInstance() {
        if (mInstance == null) {
            mInstance = new EnvironmentManager();
        }
        return mInstance;
    }

    /**
     * Returns the url of the current build type.
     * eg.
     * if BuildConfig.environment = "debug" => returns staging Environment
     *
     * @return Environment
     */
    @DebugLog
    public Environment getEnvironment() {
        Environment environment = null;
        if (BuildConfig.ENVIRONMENT.equals("STAGING")) {
            environment = Environment.STAGING;
        } else if (BuildConfig.ENVIRONMENT.equals("LIVE")) {
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
     *
     * @return String
     */
    @DebugLog
    public String getEnviromentApiUrl() {
        String apiUrl = null;
        if (BuildConfig.ENVIRONMENT.equals("STAGING")) {
            apiUrl = Constants.API_STAGING_URL;
        } else if (BuildConfig.ENVIRONMENT.equals("LIVE")) {
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
     *
     * @return RestAdapter.LogLevel
     */
    @DebugLog
    public RestAdapter.LogLevel getEnvironmentApiLogLevel() {
        RestAdapter.LogLevel logLevel = null;
        if (BuildConfig.ENVIRONMENT.equals("STAGING")) {
            logLevel = RestAdapter.LogLevel.FULL;
        } else if (BuildConfig.ENVIRONMENT.equals("LIVE")) {
            logLevel = RestAdapter.LogLevel.BASIC;
        } else if (BuildConfig.ENVIRONMENT.equals("UAT")) {
            logLevel = RestAdapter.LogLevel.FULL;
        }
        return logLevel;
    }

    /**
     * Returns the API log level according to the current build environment.
     * eg.
     * if BuildConfig.environment = "debug" => returns FULL Logging
     *
     * @return RestAdapter.LogLevel
     */
    @DebugLog
    public int getEnvironmentLogLevel() {
        int logLevel = 0;
        if (BuildConfig.ENVIRONMENT.equals("STAGING")) {
            logLevel = Log.DEBUG;
        } else if (BuildConfig.ENVIRONMENT.equals("LIVE")) {
            logLevel = Log.INFO;
        } else if (BuildConfig.ENVIRONMENT.equals("UAT")) {
            logLevel = Log.VERBOSE;
        }
        return logLevel;
    }

    /**
     * Returns true of false if I can track in google
     * analytics according to the environment
     * You can change it as you want
     *
     * @return
     */
    @DebugLog
    public boolean canTrackGA() {
        boolean canTrack = false;
        if (BuildConfig.ENVIRONMENT.equals("STAGING")) {
            canTrack = false;
        } else if (BuildConfig.ENVIRONMENT.equals("LIVE")) {
            canTrack = true;
        } else if (BuildConfig.ENVIRONMENT.equals("UAT")) {
            canTrack = true;
        }
        return canTrack;
    }
}
