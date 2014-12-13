package com.soi.rapidandroidapp.api.managers;


import android.content.Context;

import com.soi.rapidandroidapp.api.common.AbstractApiManager;
import com.soi.rapidandroidapp.api.interceptors.ApiRequestInterceptor;
import com.soi.rapidandroidapp.api.services.AppApiService;
import com.soi.rapidandroidapp.managers.EnvironmentManager;
import com.soi.rapidandroidapp.models.common.Environment;
import com.soi.rapidandroidapp.modules.Injector;

import javax.inject.Inject;

import retrofit.RestAdapter;

/**
 * Created by Spiros I. Oikonomakis on 10/17/14.
 *
 * This class handles all the api requests
 */
public class ApiManager extends AbstractApiManager<AppApiService> {

    @Inject
    EnvironmentManager environmentManager;

	private Context mContext;

    public ApiManager(Context context)
    {
		this.mContext = context;
        RestAdapter.Builder restAdapterBuilder = getDefaultRestAdapterBuilder();
        // Add extra options to rest adapter
        RestAdapter restAdapter = restAdapterBuilder.setRequestInterceptor(new ApiRequestInterceptor()).build();
        this.service = restAdapter.create(AppApiService.class);
    }

    @Override
    public Object execute(String action, Object... args) {
        return null;
    }

    @Override
    protected String getApiUrl() {
        return environmentManager.getEnviromentApiUrl();
    }
}
