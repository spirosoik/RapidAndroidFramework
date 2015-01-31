package com.soi.rapidandroidapp.api.managers;


import android.content.Context;

import com.soi.rapidandroidapp.api.common.AbstractApiManager;
import com.soi.rapidandroidapp.api.interceptors.ApiRequestInterceptor;
import com.soi.rapidandroidapp.api.services.AppApiService;
import com.soi.rapidandroidapp.managers.EnvironmentManager;
import com.soi.rapidandroidapp.utilities.Constants;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit.RestAdapter;

/**
 * Created by Spiros I. Oikonomakis on 10/17/14.
 * <p/>
 * This class handles all the api requests
 */
public class ApiManager extends AbstractApiManager<AppApiService> {

    @Inject
    EnvironmentManager environmentManager;

    private Context mContext;

    public ApiManager(Context context) {
        this.mContext = context;
        RestAdapter.Builder restAdapterBuilder = getDefaultRestAdapterBuilder();
        // Add extra options to rest adapter
        RestAdapter restAdapter = restAdapterBuilder.setRequestInterceptor(new ApiRequestInterceptor(getHeaders())).build();
        this.service = restAdapter.create(AppApiService.class);
    }

    @Override
    public Object execute(String action, Object... args) {
        return null;
    }


    @Override
    protected String getApiUrl()
    {
        return environmentManager.getEnviromentApiUrl();
    }

    @Override
    protected Map<String, String> getHeaders()
    {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(Constants.API_HEADER_HTTP_ACCEPT, Constants.API_HEADER_HTTP_ACCEPT_VALUE);
        return headers;
    }
}
