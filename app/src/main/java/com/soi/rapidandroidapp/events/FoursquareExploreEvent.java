package com.soi.rapidandroidapp.events;

import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.Explore;

/**
 * Created by Spiros I. Oikonomakis on 11/17/14.
 */
public class FoursquareExploreEvent {

    public Explore explore;

    public FoursquareExploreEvent(Explore explore)
    {
        this.explore = explore;
    }
}
