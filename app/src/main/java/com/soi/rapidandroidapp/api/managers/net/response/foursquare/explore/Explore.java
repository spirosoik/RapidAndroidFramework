package com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore;

import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common.Response;

import java.io.Serializable;

/**
 * Created by Spiros I. Oikonomakis on 11/16/14.
 */
public class Explore implements Serializable {

    public Response response;

    @Override
    public String toString() {
        return "Explore [response=" + response + "]";
    }
}
