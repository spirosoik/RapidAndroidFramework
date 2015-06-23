package com.soi.rapidandroidapp.api.errorhandlers;

import com.soi.rapidandroidapp.api.managers.events.common.HttpNotfoundEvent;
import com.soi.rapidandroidapp.api.managers.events.common.HttpServerErrorEvent;
import com.soi.rapidandroidapp.api.managers.events.common.HttpUnauthorizedEvent;
import com.soi.rapidandroidapp.api.managers.events.common.HttpUpgradeRequiredEvent;
import com.soi.rapidandroidapp.api.managers.events.common.NotInternetEvent;
import com.squareup.otto.Bus;

import java.net.HttpURLConnection;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Spiros I. Oikonomakis on 1/6/15.
 */
public class ApiErrorHandler implements ErrorHandler
{

    private Bus eventBus;

    public ApiErrorHandler(Bus bus)
    {
        this.eventBus = bus;
    }

    @Override
    public Throwable handleError(RetrofitError error)
    {
        Response r = error.getResponse();
        if (r == null && error.getMessage() != null && error.getMessage().contains("No address")) {
            eventBus.post(new NotInternetEvent());
            return error;
        }

        if (r != null) {
            if (r.getStatus() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                eventBus.post(new HttpUnauthorizedEvent());
                return error;
            }

            if (r.getStatus() == HttpURLConnection.HTTP_NOT_FOUND) {
                eventBus.post(new HttpNotfoundEvent());
                return error;
            }

            if (r.getStatus() >= HttpURLConnection.HTTP_INTERNAL_ERROR) {
                eventBus.post(new HttpServerErrorEvent());
                return error;
            }

            if (r.getStatus() == 426) {
                eventBus.post(new HttpUpgradeRequiredEvent());
                return error;
            }
        }
        return error;
    }
}
