package com.soi.rapidandroidapp.api.services;


import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.Explore;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.search.SearchResult;

import retrofit.http.GET;
import retrofit.http.Query;

public interface FoursquareApiService {

    @GET("/venues/explore")
    Explore explore( //
                     @Query("ll") String ll, //
                     @Query("client_id") String clientId, //
                     @Query("client_secret") String clientSecret, //
                     @Query("limit") Integer limit, //
                     @Query("sortByDistance") Integer sortByDistance, //
                     @Query("v") String date //
    );

    @GET("/venues/search")
    SearchResult search( //
                         @Query("intent") String intent, //
                         @Query("radius") Integer radius, //
                         @Query("ll") String ll, //
                         @Query("client_id") String clientId, //
                         @Query("client_secret") String clientSecret, //
                         @Query("query") String query, //
                         @Query("limit") Integer limit, //
                         @Query("sortByDistance") Integer sortByDistance, //
                         @Query("v") String date//
    );
}
