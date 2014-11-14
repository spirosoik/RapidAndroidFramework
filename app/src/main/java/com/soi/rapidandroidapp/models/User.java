package com.soi.rapidandroidapp.models;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.soi.rapidandroidapp.database.TABLE_NAMES;
import com.soi.rapidandroidapp.models.common.DBModel;

@Table(name = TABLE_NAMES.USER)
public class User extends DBModel<User> {

	public static String AUTH_TOKEN_KEY = "auth_token";

    @Column(name = "user_id", index = true, unique = true)
    public Long userId;

    @Column(name = "token", index = true, unique = true)
    public String authToken;

    @Column(name = "email", index = true, unique = true)
    public String email;

    @Column(name = "fname")
    public String fname;

    @Column(name = "lname")
    public String lname;

    @Column(name = "avatar")
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