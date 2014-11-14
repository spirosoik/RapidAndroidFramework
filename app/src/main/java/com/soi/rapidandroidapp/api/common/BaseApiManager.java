package com.soi.rapidandroidapp.api.common;

import retrofit.RestAdapter;

/**
 * Created by Spiros I. Oikonomakis on 11/4/14.
 */
public interface BaseApiManager<T> {

    /**
     *  Sets the current api service
     */
    void setApiService(T service);

    /**
     * return the current api service
     * @return
     */
    T getApiService();

    /**
     * Initialize rest adapter and returns the RetroFit rest adapter
     * @return
     */
    RestAdapter.Builder getDefaultRestAdapterBuilder();
}
