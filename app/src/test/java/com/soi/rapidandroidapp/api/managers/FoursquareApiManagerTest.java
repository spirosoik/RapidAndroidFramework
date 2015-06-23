package com.soi.rapidandroidapp.api.managers;

import android.test.InstrumentationTestCase;

import com.soi.rapidandroidapp.TestBaseApplication;
import com.soi.rapidandroidapp.api.managers.events.FoursquareExploreEvent;
import com.soi.rapidandroidapp.api.managers.events.FoursquareSearchEvent;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.Explore;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common.Response;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.search.SearchResult;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.search.common.SearchResponse;
import com.soi.rapidandroidapp.events.common.BusProvider;
import com.soi.rapidandroidapp.test.support.UnitTestSpecification;
import com.soi.rapidandroidapp.utilities.Constants;
import com.squareup.otto.Subscribe;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Spiros I. Oikonomakis on 10/17/14.
 * <p/>
 * This class handles all the api requests
 */
@Config(emulateSdk = 18)
public class FoursquareApiManagerTest extends UnitTestSpecification {
    
    @Inject
    FoursquareApiManager foursquareApiManager;
    
    private String intent;
    private Integer radius;
    private String ll;
    private String clientId;
    private String clientSecret;
    private int limit;
    private int sortByDistance;
    private String v;
    private CountDownLatch signal;

    @Before
    public void setup() throws Exception
    {

        MockitoAnnotations.initMocks(this);

        TestBaseApplication.injectMocks(this);

        intent = "global";
        radius = 5000;
        ll = "40.7,-74";
        clientId = Constants.FOURSQUARE_API_CLIENT_ID;
        clientSecret = Constants.FOURSQUARE_API_CLIENT_SECRET;
        limit = 15;
        sortByDistance = 1;
        v = FoursquareApiManager.dateFormatter.format(new Date());
    }

    @Test
    public void testFoursquareExplore() throws Throwable {
        signal = new CountDownLatch(1);
        new Runnable() {
            @Override
            public void run() {
                foursquareApiManager.explore(ll, clientId, clientSecret, limit, sortByDistance, v);
                BusProvider.getInstance().register(new Object() {
                    @Subscribe
                    public void onFoursquareExplore(FoursquareExploreEvent event) {
                        signal.countDown();
                        assertThat(event).isNotNull();

                        Explore explore = event.response;
                        assertThat(explore).isNotNull();

                        Response response = explore.response;
                        assertThat(response).isNotNull();
                        assertThat(response.groups).isNotNull();
                    }
                });

            }
        };
        signal.await(15, TimeUnit.SECONDS);
    }

    @Test
    public void testFoursquareSearch() throws Throwable {
        signal = new CountDownLatch(1);
        new Runnable() {
            @Override
            public void run() {
                foursquareApiManager.search(intent, radius, ll, clientId, clientSecret, "ath", limit, sortByDistance, v);
                BusProvider.getInstance().register(new Object() {
                    @Subscribe
                    public void onFoursquareExplore(FoursquareSearchEvent event) {
                        signal.countDown();
                        assertThat(event).isNotNull();

                        SearchResult searchResult = event.response;
                        assertThat(searchResult).isNotNull();

                        SearchResponse response = searchResult.response;
                        assertThat(response).isNotNull();
                        assertThat(response.venues).isNotNull();
                    }
                });

            }
        };
        signal.await(15, TimeUnit.SECONDS);
    }

}
