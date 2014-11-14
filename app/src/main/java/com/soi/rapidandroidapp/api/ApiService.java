package com.soi.rapidandroidapp.api;


import com.soi.rapidandroidapp.models.User;

import retrofit.http.Field;
import retrofit.http.POST;

public interface ApiService {

    @POST("/login")
    User login(
            @Field("email") String email,
            @Field("password") String password
    );
}
