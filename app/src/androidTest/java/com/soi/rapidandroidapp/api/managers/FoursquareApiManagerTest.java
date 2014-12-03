package com.soi.rapidandroidapp.api.managers;


import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;
import android.util.Log;

import com.soi.rapidandroidapp.api.managers.common.BaseEvent;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.Explore;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common.Response;
import com.soi.rapidandroidapp.api.managers.events.FoursquareExploreEvent;
import com.soi.rapidandroidapp.events.common.BusProvider;
import com.soi.rapidandroidapp.utilities.Constants;
import com.squareup.otto.Subscribe;

import java.util.Date;

/**
 * Created by Spiros I. Oikonomakis on 10/17/14.
 *
 * This class handles all the api requests
 */
public class FoursquareApiManagerTest extends InstrumentationTestCase {

    private String intent;
    private Integer radius;
    private String ll;
    private String clientId;
    private String clientSecret;
    private int limit;
    private int sortByDistance;
    private String v;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        intent = "global";
        radius = 5000;
        ll = "40.7,-74";
        clientId     = Constants.FOURSQUARE_API_CLIENT_ID;
        clientSecret = Constants.FOURSQUARE_API_CLIENT_SECRET;
        limit = 15;
        sortByDistance = 1;
        v = FoursquareApiManager.dateFormatter.format(new Date());

    }

    public void testFoursquareExplore() throws Throwable {
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                BusProvider.getInstance().register(this);

                FoursquareApiManager.getInstance().explore(ll, clientId, clientSecret, limit, sortByDistance, v);
            }
        });
    }

    @Subscribe
    public void onFoursquareExplore(FoursquareExploreEvent event)
    {
        assertNull(event);

        Explore explore = event.response;
        assertNotNull(explore);

        Response response = explore.response;
        assertNotNull(response);
        assertNotNull(response.groups);
    }

}
