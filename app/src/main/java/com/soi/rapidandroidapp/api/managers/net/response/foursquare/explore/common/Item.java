package com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common;

import java.io.Serializable;

/**
 * Created by Spiros I. Oikonomakis on 11/16/14.
 */
public class Item implements Serializable {
    public Venue venue;

    @Override
    public String toString() {
        return "Item [venue=" + venue + "]";
    }
}
