package com.soi.rapidandroidapp.database;

import android.provider.BaseColumns;

/**
 * Created by Spiros I. Oikonomakis on 12/9/14.
 */
public final class UserContract {

    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USERID = "user_id";
        public static final String COLUMN_NAME_TOKEN  = "token";
        public static final String COLUMN_NAME_EMAIL  = "email";
        public static final String COLUMN_NAME_FNAME  = "fname";
        public static final String COLUMN_NAME_LNAME  = "lname";
        public static final String COLUMN_NAME_AVATAR = "avatar";
    }
}
