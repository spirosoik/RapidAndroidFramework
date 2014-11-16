package com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Spiros I. Oikonomakis on 11/16/14.
 */
public class Group implements Serializable {

    public String type;
    public String name;
    public ArrayList<Item> items;

    @Override
    public String toString() {
        return "Group [type=" + type + ", name=" + name + ", items=" + items
                + "]";
    }
}
