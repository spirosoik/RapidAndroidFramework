package com.soi.rapidandroidapp.api.managers.net.response.foursquare.search.common;

import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common.Venue;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Spiros I. Oikonomakis on 11/16/14.
 */
public class SearchResponse implements Serializable {

    public List<Venue> venues;
}
