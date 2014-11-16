package com.soi.rapidandroidapp.api.managers;


import android.test.InstrumentationTestCase;

import com.soi.rapidandroidapp.api.managers.FoursquareApiManager;
import com.soi.rapidandroidapp.utilities.Constants;

import java.util.Date;

/**
 * Created by Spiros I. Oikonomakis on 10/17/14.
 *
 * This class handles all the api requests
 */
public class FoursquareApiManagerTest extends InstrumentationTestCase {

    private String ll;
    private String clientId;
    private String clientSecret;
    private int limit;
    private int sortByDistance;
    private String v;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ll = "40.7,-74";
        clientId     = Constants.FOURSQUARE_API_CLIENT_ID;
        clientSecret = Constants.FOURSQUARE_API_CLIENT_SECRET;
        limit = 10;
        sortByDistance = 1;
        v = FoursquareApiManager.dateFormatter.format(new Date());

    }

    public void testFoursquareExplore()
    {
        FoursquareApiManager.getInstance().getApiService().explore(ll,clientId,clientSecret,limit,sortByDistance,v);
    }
}
