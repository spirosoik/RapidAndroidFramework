package com.soi.rapidandroidapp.api.managers.tasks.common;

import android.os.AsyncTask;

import com.soi.rapidandroidapp.api.common.AbstractApiManager;
import com.soi.rapidandroidapp.api.common.ApiRequestAttrs;
import com.soi.rapidandroidapp.events.common.BusProvider;

/**
 * Created by Spiros I. Oikonomakis on 11/16/14.
 */
public abstract class AbstractAsyncTask extends AsyncTask<ApiRequestAttrs, Void, Object> {

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(ApiRequestAttrs... params)
    {
        ApiRequestAttrs apiRequestAttrs = params[0];
        AbstractApiManager apiManager = apiRequestAttrs.getApiManager();
        String resource = apiRequestAttrs.getResource();
        return apiManager.execute(apiManager.getApiService(), resource);
    }

    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
        BusProvider.getInstance().post(o);
    }
}
