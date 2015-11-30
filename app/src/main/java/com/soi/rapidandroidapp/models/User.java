package com.soi.rapidandroidapp.models;

import com.soi.rapidandroidapp.database.UserContract;
import com.soi.rapidandroidapp.models.common.DBModel;
import ollie.annotation.Column;
import ollie.annotation.NotNull;
import ollie.annotation.Table;
import ollie.annotation.Unique;

@Table(UserContract.UserEntry.TABLE_NAME)
public class User extends DBModel<User> {

    public static String AUTH_TOKEN_KEY = "auth_token";

    @Column(value= UserContract.UserEntry.COLUMN_NAME_USERID)
    @Unique
    @NotNull
    public Long userId;

    @Column(value= UserContract.UserEntry.COLUMN_NAME_TOKEN)
    @Unique
    @NotNull
    public String authToken;

    @Column(value= UserContract.UserEntry.COLUMN_NAME_EMAIL)
    @Unique
    @NotNull
    public String email;

    @Column(value= UserContract.UserEntry.COLUMN_NAME_FNAME)
    public String fname;

    @Column(value= UserContract.UserEntry.COLUMN_NAME_LNAME)
    public String lname;

    @Column(value= UserContract.UserEntry.COLUMN_NAME_AVATAR)
    public String avatar;

    public User() {
    }

    public User(Long userId, String authToken) {
        this.userId = userId;
        this.authToken = authToken;
    }

    public Long getUserId() {
        return userId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getAvatar() {
        return avatar;
    }

}