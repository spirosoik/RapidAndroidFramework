package com.soi.rapidandroidapp.api.managers.net.response.foursquare.search;

import com.soi.rapidandroidapp.api.managers.net.response.foursquare.search.common.SearchMeta;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.search.common.SearchResponse;

import java.io.Serializable;

/**
 * Created by Spiros I. Oikonomakis on 11/16/14.
 */
public class SearchResult implements Serializable {

    public SearchMeta meta;
    public SearchResponse response;
}
