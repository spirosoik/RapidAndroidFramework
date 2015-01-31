package com.soi.rapidandroidapp;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.soi.rapidandroidapp.modules.Injector;
import com.soi.rapidandroidapp.modules.RootModule;

import java.util.HashMap;

/**
 * Created by spirosoikonomakis on 3/9/14.
 */
public class BaseApplication extends Application {

    public final static String APP_NAME = "RapidAndroid";
    private static BaseApplication instance;
    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public BaseApplication() {

    }

    /**
     * Create main application
     *
     * @param context
     */
    public BaseApplication(final Context context) {
        super();
        attachBaseContext(context);

    }

    /**
     * Create main application
     *
     * @param instrumentation
     */
    public BaseApplication(final Instrumentation instrumentation) {
        super();
        attachBaseContext(instrumentation.getTargetContext());
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(getBaseContext());

        instance = this;

        // Perform injection
        Injector.init(getRootModule(), this);

    }

    public synchronized Tracker getTracker(TrackerName trackerId) {

        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(getString(R.string.ga_property_id));
            mTrackers.put(trackerId, t);
        }
        return mTrackers.get(trackerId);
    }

    private Object getRootModule() {
        return new RootModule();
    }

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }
}