package com.soi.rapidandroidapp.api.common;

import android.util.Log;

import com.soi.rapidandroidapp.api.GsonInstance;
import com.soi.rapidandroidapp.api.managers.common.AbstractAsyncTask;
import com.soi.rapidandroidapp.managers.EnvironmentManager;
import com.soi.rapidandroidapp.utilities.ReflectionUtils;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Spiros I. Oikonomakis on 11/4/14.
 * An abstract API manager which can be extended by each new API manager
 */
public abstract class AbstractApiManager<T> extends AbstractAsyncTask implements BaseApiManager<T> {

    public static final String TAG_LOG_NAME = Class.class.getSimpleName();

    public T service;
    private GsonInstance gsonInstance = new GsonInstance();

    @Override
    public RestAdapter.Builder getDefaultRestAdapterBuilder()
    {
        OkHttpClient httpClient = new OkHttpClient();

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(EnvironmentManager.getInstance().getEnviromentApiUrl())
                .setClient(new OkClient(httpClient))
                .setConverter(new GsonConverter(GsonInstance.gson))
                .setLogLevel(EnvironmentManager.getInstance().getEnvironmentLogLevel())
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
