package com.soi.rapidandroidapp.modules;

import android.content.Context;

import com.soi.rapidandroidapp.BaseApplication;
import com.soi.rapidandroidapp.api.ApiManager;
import com.soi.rapidandroidapp.helpers.DialogsHelper;
import com.soi.rapidandroidapp.managers.SessionManager;
import com.soi.rapidandroidapp.ui.LoginActivity;
import com.soi.rapidandroidapp.ui.common.BaseFragmentActivity;
import com.soi.rapidandroidapp.utilities.Utils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by spirosoikonomakis on 3/9/14.
 */
@Module
(
        complete = false,

        injects = {
                BaseApplication.class,
                BaseFragmentActivity.class,
                LoginActivity.class
        },
        library = true
)
public class AppModule {

    @Provides
    @Singleton
    Utils provideUtilities()
    {

        return Utils.getInstance();
    }

    @Provides
    @Singleton
    DialogsHelper provideDialogs()
    {

        return DialogsHelper.getInstance();
    }

    @Provides
    SessionManager provideSessionManager(Context context)
    {
        return new SessionManager(context);
    }

    @Provides
    @Singleton
    ApiManager provideApiManager()
    {
        return ApiManager.getInstance();
    }

}
