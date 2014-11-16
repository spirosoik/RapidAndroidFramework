package com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common;

import java.io.Serializable;

/**
 * Created by Spiros I. Oikonomakis on 11/16/14.
 */
public class Location implements Serializable {

    public String address;
    public float lat;
    public float lng;
    public int distance;
    public String city;
    public String country;

    @Override
    public String toString() {
        return "Location [address=" + address + ", lat=" + lat + ", lng=" + lng
                + ", distance=" + distance + ", city=" + city + ", country="
                + country + "]";
    }
}
