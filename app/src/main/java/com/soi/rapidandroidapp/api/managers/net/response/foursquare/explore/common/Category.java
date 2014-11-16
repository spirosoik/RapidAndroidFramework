package com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common;

import java.io.Serializable;

/**
 * Created by Spiros I. Oikonomakis on 11/16/14.
 */
public class Category implements Serializable {

    public String id;
    public String name;

    public Icon icon;

    @Override
    public String toString() {
        return "Categories [id=" + id + ", name=" + name + ", icon=" + icon
                + "]";
    }
}
