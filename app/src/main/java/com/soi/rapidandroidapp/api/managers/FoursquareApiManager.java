package com.soi.rapidandroidapp.api.managers;

import com.soi.rapidandroidapp.api.common.AbstractApiManager;
import com.soi.rapidandroidapp.api.interceptors.ApiRequestInterceptor;
import com.soi.rapidandroidapp.api.interceptors.FoursquareApiRequestInterceptor;
import com.soi.rapidandroidapp.api.managers.tasks.foursquare.FoursquareResources;
import com.soi.rapidandroidapp.api.services.FoursquareApiService;
import com.soi.rapidandroidapp.utilities.Constants;
import com.soi.rapidandroidapp.utilities.StringUtils;

import java.text.SimpleDateFormat;

import retrofit.RestAdapter;

/**
 * Created by Spiros I. Oikonomakis on 10/17/14.
 *
 * This class handles all the api requests
 */
public class FoursquareApiManager extends AbstractApiManager<FoursquareApiService> {

    private static FoursquareApiManager mInstance;
    public static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

    public static synchronized FoursquareApiManager getInstance()
    {
        if (mInstance == null)
            mInstance = new FoursquareApiManager();

        return mInstance;
    }

    public FoursquareApiManager()
    {
        RestAdapter.Builder restAdapterBuilder = this.getDefaultRestAdapterBuilder();
        restAdapterBuilder
                .setEndpoint(Constants.FOURSQUARE_API_CLIENT_ID)
                .setRequestInterceptor(new FoursquareApiRequestInterceptor());
        // Add extra options to rest adapter
        RestAdapter restAdapter = restAdapterBuilder.setRequestInterceptor(new ApiRequestInterceptor()).build();
        this.service = restAdapter.create(FoursquareApiService.class);
    }

    @Override
    public Object execute(String action, Object... args) {
        if (StringUtils.equals(action, FoursquareResources.EXPLORE)) {
            return FoursquareApiManager.getInstance().getApiService().explore(
                    (String) args[0],
                    (String)  args[1],
                    (String) args[2],
                    (Integer) args[3],
                    (Integer) args[4],
                    (String) args[5]);
        } else if (StringUtils.equals(action, FoursquareResources.SEARCH)) {
            return FoursquareApiManager.getInstance().getApiService().search(
                    "global",
                    (Integer) args[0],
                    (String) args[1],
                    (String)  args[2],
                    (String) args[3],
                    (String) args[4],
                    (Integer) args[5],
                    (Integer) args[6],
                    (String) args[7]);
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
