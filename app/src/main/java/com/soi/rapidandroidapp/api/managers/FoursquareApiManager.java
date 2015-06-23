package com.soi.rapidandroidapp.api.managers;

import android.content.Context;

import com.soi.rapidandroidapp.api.common.AbstractApiManager;
import com.soi.rapidandroidapp.api.common.ApiRequestAttrs;
import com.soi.rapidandroidapp.api.interceptors.FoursquareApiRequestInterceptor;
import com.soi.rapidandroidapp.api.managers.events.FoursquareExploreEvent;
import com.soi.rapidandroidapp.api.managers.events.FoursquareSearchEvent;
import com.soi.rapidandroidapp.api.managers.foursquare.FoursquareResources;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.Explore;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.search.SearchResult;
import com.soi.rapidandroidapp.api.services.FoursquareApiService;
import com.soi.rapidandroidapp.utilities.Constants;
import com.soi.rapidandroidapp.utilities.ReflectionUtils;
import com.soi.rapidandroidapp.utilities.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Map;

import retrofit.RestAdapter;

/**
 * Created by Spiros I. Oikonomakis on 10/17/14.
 * <p/>
 * This class handles all the api requests
 */
public class FoursquareApiManager extends AbstractApiManager<FoursquareApiService> {


    public static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
    private Context mContext;

    public FoursquareApiManager(Context context) {
        this.mContext = context;
        RestAdapter.Builder restAdapterBuilder = getDefaultRestAdapterBuilder();
        // Add extra options to rest adapter
        RestAdapter restAdapter = restAdapterBuilder
                .setRequestInterceptor(new FoursquareApiRequestInterceptor()).build();
        this.service = restAdapter.create(FoursquareApiService.class);
    }

    @Override
    public String getApiUrl() {
        return Constants.FOURSQUARE_API_URL;
    }

    @Override
    protected Map<String, String> getHeaders() {
        return null;
    }

    @Override
    public Object execute(String action, Object... args) {
        Object o = ReflectionUtils.tryInvoke(getApiService(), action, args);
        if (StringUtils.equals(action, FoursquareResources.EXPLORE)) {
            return new FoursquareExploreEvent((Explore) o);
        } else if (StringUtils.equals(action, FoursquareResources.SEARCH)) {
            return new FoursquareSearchEvent((SearchResult) o);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Get places from Foursquare
     */
    public void explore(String ll, String clientId, String clientSecret,
                        int limit, int sortByDistance, String v) {
        ApiRequestAttrs attrs = new ApiRequestAttrs(this, FoursquareResources.EXPLORE, ll, clientId,
                clientSecret, limit, sortByDistance, v);
        super.execute(attrs);
    }

    /**
     * Get places from Foursquare
     */
    public void search(String intent, Integer radius, String ll, String clientId, String clientSecret, //
                       String query, Integer limit, Integer sortByDistance, String v) {
        ApiRequestAttrs attrs = new ApiRequestAttrs(this, FoursquareResources.SEARCH, intent, radius,
                ll, clientId, clientSecret, query, limit, sortByDistance, v);
        super.execute(attrs);
    }
}
