package com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Spiros I. Oikonomakis on 11/16/14.
 */
public class Venue implements Serializable {

    public String id;
    public String name;
    public Location location;
    public ArrayList<Category> categories;

    @Override
    public String toString() {
        return "Venue [id=" + id + ", name=" + name + ", location=" + location
                + ", categories=" + categories + "]";
    }
}
