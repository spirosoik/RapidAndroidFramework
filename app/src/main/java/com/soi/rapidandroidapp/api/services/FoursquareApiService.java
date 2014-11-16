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
                     @Query("limit") int limit, //
                     @Query("sortByDistance") int sortByDistance, //
                     @Query("v") String date //
    );

    @GET("/venues/search")
    SearchResult search( //
                         @Query("intent") String intent, //
                         @Query("radius") int radius, //
                         @Query("ll") String ll, //
                         @Query("client_id") String clientId, //
                         @Query("client_secret") String clientSecret, //
                         @Query("query") String query, //
                         @Query("limit") int limit, //
                         @Query("sortByDistance") int sortByDistance, //
                         @Query("v") String date//
    );
}
