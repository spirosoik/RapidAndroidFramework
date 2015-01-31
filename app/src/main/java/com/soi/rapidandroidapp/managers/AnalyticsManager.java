package com.soi.rapidandroidapp.managers;

import android.app.Activity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.soi.rapidandroidapp.BaseApplication;

/**
 * Created by soikonomakis on 5/5/14.
 */
public class AnalyticsManager {

    private static AnalyticsManager _instance;
    private Activity act;
    private Tracker tracker;

    public static synchronized AnalyticsManager getInstance() {
        if (_instance == null)
            _instance = new AnalyticsManager();
        return _instance;
    }

    /**
     * Initialize the google analytics tracked
     *
     * @param act
     * @param trackerName
     * @return
     */
    public AnalyticsManager initTracker(Activity act, BaseApplication.TrackerName trackerName) {
        this.act = act;
        tracker = ((BaseApplication) act.getApplication()).getTracker(trackerName);
        return _instance;
    }

    /**
     * Send an event of an actions
     *
     * @param category
     * @param action
     * @param label
     * @return
     */
    public AnalyticsManager trackEvent(String category, String action, String label) {
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label).build());
        return _instance;
    }

    /**
     * Sends a screen page view
     *
     * @param screenName
     */
    public void trackScreenView(String screenName) {
        tracker.setScreenName(screenName);

        // Send a screen view.
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

}