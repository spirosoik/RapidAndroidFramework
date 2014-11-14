package com.soi.rapidandroidapp.api;


import com.soi.rapidandroidapp.api.common.AbstractApiManager;
import com.soi.rapidandroidapp.api.interceptors.ApiRequestInterceptor;
import com.soi.rapidandroidapp.api.services.AppApiService;

import retrofit.RestAdapter;

/**
 * Created by Spiros I. Oikonomakis on 10/17/14.
 *
 * This class handles all the api requests
 */
public class ApiManager extends AbstractApiManager<AppApiService> {

    private static ApiManager mInstance;

    public static synchronized ApiManager getInstance()
    {
        if (mInstance == null)
            mInstance = new ApiManager();
        return mInstance;
    }

    public ApiManager()
    {
        RestAdapter.Builder restAdapterBuilder = this.getDefaultRestAdapterBuilder();
        // Add extra options to rest adapter
        RestAdapter restAdapter = restAdapterBuilder.setRequestInterceptor(new ApiRequestInterceptor()).build();
        this.service = restAdapter.create(AppApiService.class);
    }
}
