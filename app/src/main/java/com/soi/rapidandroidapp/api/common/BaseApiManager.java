package com.soi.rapidandroidapp.api.common;

import retrofit.RestAdapter;

/**
 * Created by Spiros I. Oikonomakis on 11/4/14.
 */
public interface BaseApiManager<T> {

    /**
     * return the current api service
     *
     * @return
     */
    T getApiService();

    /**
     * Sets the current api service
     */
    void setApiService(T service);

    /**
     * Initialize rest adapter and returns the RetroFit rest adapter
     *
     * @return
     */
    RestAdapter.Builder getDefaultRestAdapterBuilder();

    /**
     * Executes the proper API resource via Reflection
     *
     * @param target
     * @param methodName
     * @param args
     */
    Object execute(Object target, String methodName, Object... args);

    /**
     * Executes the proper API resource for each action separately
     *
     * @param action
     * @param args
     */
    Object execute(String action, Object... args);
}
