package com.soi.rapidandroidapp.models;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.soi.rapidandroidapp.database.TABLE_NAMES;
import com.soi.rapidandroidapp.database.UserContract;
import com.soi.rapidandroidapp.models.common.DBModel;

@Table(name = UserContract.UserEntry.TABLE_NAME)
public class User extends DBModel<User> {

	public static String AUTH_TOKEN_KEY = "auth_token";

    @Column(name = UserContract.UserEntry.COLUMN_NAME_USERID, index = true, unique = true)
    private Long userId;

    @Column(name = UserContract.UserEntry.COLUMN_NAME_TOKEN, index = true, unique = true)
    private String authToken;

    @Column(name = UserContract.UserEntry.COLUMN_NAME_EMAIL, index = true, unique = true)
    private String email;

    @Column(name = UserContract.UserEntry.COLUMN_NAME_FNAME)
    private String fname;

    @Column(name = UserContract.UserEntry.COLUMN_NAME_LNAME)
    private String lname;

    @Column(name = UserContract.UserEntry.COLUMN_NAME_AVATAR)
    private String avatar;

    private User() {
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