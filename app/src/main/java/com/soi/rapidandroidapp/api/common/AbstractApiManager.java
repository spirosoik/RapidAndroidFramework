package com.soi.rapidandroidapp.api.common;

import android.util.Log;

import com.soi.rapidandroidapp.api.GsonInstance;
import com.soi.rapidandroidapp.api.errorhandlers.ApiErrorHandler;
import com.soi.rapidandroidapp.api.managers.common.AbstractAsyncTask;
import com.soi.rapidandroidapp.events.common.BusProvider;
import com.soi.rapidandroidapp.managers.EnvironmentManager;
import com.soi.rapidandroidapp.modules.Injector;
import com.soi.rapidandroidapp.utilities.ReflectionUtils;
import com.squareup.okhttp.OkHttpClient;

import java.util.Map;

import javax.inject.Inject;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Spiros I. Oikonomakis on 11/4/14.
 * An abstract API manager which can be extended by each new API manager
 */
public abstract class AbstractApiManager<T> extends AbstractAsyncTask implements BaseApiManager<T> {

    protected static final String TAG_LOG_NAME = Class.class.getSimpleName();
    
    public T service;
    
    @Inject
    EnvironmentManager environmentManager;
    
    private GsonInstance gsonInstance = new GsonInstance();

    /**
     * Get current url for the requested api manager 
     * @return
     */
    protected abstract String getApiUrl();

    /**
     * Returns the custom header which will be intercept in each request from
     * the api manager
     * @return
     */
    protected abstract Map<String, String> getHeaders();

    protected AbstractApiManager() 
    {
        Injector.inject(this);
    }

    @Override
    public RestAdapter.Builder getDefaultRestAdapterBuilder() {
        OkHttpClient httpClient = new OkHttpClient();

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(getApiUrl())
                .setErrorHandler(new ApiErrorHandler(BusProvider.getInstance()))
                .setClient(new OkClient(new OkHttpClient()))
                .setConverter(new GsonConverter(GsonInstance.gson))
                .setLogLevel(environmentManager.getEnvironmentApiLogLevel())
                .setLog(new RestAdapter.Log() { //
                    @Override
                    public void log(String msg) {
                        Log.i(TAG_LOG_NAME, msg);
                    }
                });
        return builder;
    }

    @Override
    public T getApiService() {
        return this.service;
    }

    @Override
    public void setApiService(T service) {
        this.service = service;
    }

    @Override
    public Object execute(Object target, String methodName, Object... args) {
        return ReflectionUtils.tryInvoke(target, methodName, args);
    }
}
