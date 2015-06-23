package com.soi.rapidandroidapp.api.managers.events;

import com.soi.rapidandroidapp.api.managers.common.BaseEvent;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.Explore;

/**
 * Created by Spiros I. Oikonomakis on 11/25/14.
 */
public class FoursquareExploreEvent extends BaseEvent {

    public Explore response;

    public FoursquareExploreEvent(Explore response) {
        super(response);
        this.response = response;
    }

    public Explore getResponse() {
        return response;
    }

    public void setResponse(Explore response) {
        this.response = response;
    }
}
