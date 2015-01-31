package com.soi.rapidandroidapp.ui;

import android.os.Bundle;

import com.soi.rapidandroidapp.R;
import com.soi.rapidandroidapp.api.managers.ApiManager;
import com.soi.rapidandroidapp.api.managers.FoursquareApiManager;
import com.soi.rapidandroidapp.api.managers.events.FoursquareExploreEvent;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.Explore;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common.Response;
import com.soi.rapidandroidapp.ui.common.AbstractActivity;
import com.soi.rapidandroidapp.utilities.Constants;
import com.squareup.otto.Subscribe;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Provider;


public class MainActivity extends AbstractActivity {
    
    @Inject
    Provider<FoursquareApiManager> foursquareApiManagerProvider;
    
    private String ll;
    private String clientId;
    private String clientSecret;
    private int limit;
    private int sortByDistance;
    private String v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll = "40.7,-74";
        clientId = Constants.FOURSQUARE_API_CLIENT_ID;
        clientSecret = Constants.FOURSQUARE_API_CLIENT_SECRET;
        limit = 15;
        sortByDistance = 1;
        v = FoursquareApiManager.dateFormatter.format(new Date());
        
        if (foursquareApiManagerProvider.get() != null) {
            foursquareApiManagerProvider.get().explore(ll, clientId, clientSecret, limit, sortByDistance, v);
        }
    }

    @Subscribe
    public void onFoursquareExplore(FoursquareExploreEvent event) {
        Explore explore = event.response;

        Response response = explore.response;
    }
}
