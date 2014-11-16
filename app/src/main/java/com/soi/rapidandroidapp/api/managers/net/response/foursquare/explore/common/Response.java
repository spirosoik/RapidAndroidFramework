package com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Spiros I. Oikonomakis on 11/16/14.
 */
public class Response implements Serializable {

    public ArrayList<Group> groups;

    @Override
    public String toString() {
        return "Response [groups=" + groups + "]";
    }
}
