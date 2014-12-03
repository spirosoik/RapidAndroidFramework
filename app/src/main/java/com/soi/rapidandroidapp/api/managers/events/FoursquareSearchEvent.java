package com.soi.rapidandroidapp.api.managers.events;

import com.soi.rapidandroidapp.api.managers.common.BaseEvent;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.search.SearchResult;

/**
 * Created by Spiros I. Oikonomakis on 11/25/14.
 */
public class FoursquareSearchEvent extends BaseEvent {
    public SearchResult response;

    public FoursquareSearchEvent(SearchResult response) {
        super(response);
        this.response = response;
    }
}
