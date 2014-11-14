package com.soi.rapidandroidapp.managers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.soi.rapidandroidapp.ui.LoginActivity;

import java.util.HashMap;

public class SessionManager {

    // Android's Shared preferences
    // to create a new session for a user
    private SharedPreferences _pref;

    // The shared preferences editor
    private Editor _editor;

    private Context _context;

    // Shared pref mode
    protected int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "PocketTourPref";

    // All shared preferences key
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name
    public static final String KEY_NAME = "name";

    // Login session token
    public static final String KEY_TOKEN = "auth_token";

    public  static final String KEY_USER_EMAIL        = "email";
    public  static final String KEY_PASSWORD          = "password";
    public  static final String KEY_USER_ID           = "userId";
    public  static final String KEY_SESSION_COOKIE_ID = "cookieId";
    public  static final String KEY_FB_TOKEN          = "fbToken";
    public  static final String KEY_FIRST_TIME        = "firstTime";
    public  static final String KEY_USER_FB_EMAIL     = "mail";


    public SessionManager(Context context)
    {
        super();
        this._context = context;
        _pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        _editor = _pref.edit();
    }

    /**
     * Create login session
     * @param name
     * @param email
     * @param token
     */
    public void createLoginSession(String name, String email, String token, String userId)
    {
        _editor.putBoolean(IS_LOGIN, true);
        _editor.putString(KEY_NAME, name);
        _editor.putString(KEY_USER_EMAIL, email);
        _editor.putString(KEY_TOKEN, token);
        _editor.putString(KEY_USER_ID, userId);
        _editor.commit();
    }

    /**
     * Create FB session
     * @param userEmail
     */
    public void createFbEmailSession(String userEmail)
    {
        _editor.putString(KEY_USER_FB_EMAIL, userEmail);
        _editor.commit();

    }

    public void setUserFullName(String fullName)
    {
        _editor.putString(KEY_NAME,fullName);
        _editor.commit();
    }

    /**
     * Get Stored Session data
     */
    public HashMap<String, String> getUserDetails()
    {

        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_USER_EMAIL, _pref.getString(KEY_USER_EMAIL, null));
        user.put(KEY_PASSWORD, _pref.getString(KEY_PASSWORD, null));
        user.put(KEY_USER_ID, _pref.getString(KEY_USER_ID, null));
        user.put(KEY_TOKEN, _pref.getString(KEY_TOKEN, null));
        user.put(KEY_NAME, _pref.getString(KEY_NAME, null));
        return user;
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin()
    {
        // Check login status
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    /**
     * Clear session details
     * */
    public void logoutUser()
    {
        // Clearing all data from Shared Preferences
        _editor.clear();
        _editor.commit();

        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    /**
     * Check if user is logged in
     * @return
     */
    public boolean isLoggedIn()
    {
        return _pref.getBoolean(IS_LOGIN, false);
    }
}