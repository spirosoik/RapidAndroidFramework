package com.soi.rapidandroidapp.api.services;

import com.soi.rapidandroidapp.models.User;

import retrofit.http.Field;
import retrofit.http.POST;

/**
 * Created by Spiros I. Oikonomakis on 10/31/14.
 */
public interface AppApiService {

    @POST("/login")
    User login(
            @Field("email") String email,
            @Field("password") String password
    );

}
