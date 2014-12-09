package com.soi.rapidandroidapp.api.common;

import android.util.Log;

import com.soi.rapidandroidapp.api.GsonInstance;
import com.soi.rapidandroidapp.api.managers.common.AbstractAsyncTask;
import com.soi.rapidandroidapp.managers.EnvironmentManager;
import com.soi.rapidandroidapp.modules.Injector;
import com.soi.rapidandroidapp.utilities.ReflectionUtils;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Inject;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Spiros I. Oikonomakis on 11/4/14.
 * An abstract API manager which can be extended by each new API manager
 */
public abstract class AbstractApiManager<T> extends AbstractAsyncTask implements BaseApiManager<T> {

    @Inject
    EnvironmentManager environmentManager;

    protected static final String TAG_LOG_NAME = Class.class.getSimpleName();

    protected abstract String getApiUrl();

    public T service;

    private GsonInstance gsonInstance = new GsonInstance();

    protected AbstractApiManager() {
        Injector.inject(this);
    }

    @Override
    public RestAdapter.Builder getDefaultRestAdapterBuilder()
    {
        OkHttpClient httpClient = new OkHttpClient();

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(getApiUrl())
                .setClient(new OkClient(httpClient))
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
    public void setApiService(T service)
    {
        this.service = service;
    }

    @Override
    public T getApiService()
    {
        return this.service;
    }

    @Override
    public Object execute(Object target, String methodName, Object... args)
    {
        return ReflectionUtils.tryInvoke(target, methodName, args);
    }
}
