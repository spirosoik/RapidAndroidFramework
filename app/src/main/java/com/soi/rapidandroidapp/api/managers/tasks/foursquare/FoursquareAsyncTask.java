package com.soi.rapidandroidapp.api.managers.tasks.foursquare;

import android.content.Context;

import com.soi.rapidandroidapp.api.common.ApiRequestAttrs;
import com.soi.rapidandroidapp.api.managers.FoursquareApiManager;
import com.soi.rapidandroidapp.api.managers.tasks.common.AbstractAsyncTask;
import com.soi.rapidandroidapp.events.common.BusProvider;

/**
 * Created by Spiros I. Oikonomakis on 11/17/14.
 */
public class FoursquareAsyncTask extends AbstractAsyncTask{

    private Context mContext;

    public FoursquareAsyncTask(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Get places from Foursquare
     */
    public void getExplore()
    {
        ApiRequestAttrs attrs = new ApiRequestAttrs(FoursquareApiManager.getInstance(), FoursquareResources.EXPLORE);
        execute(attrs);
    }
}
