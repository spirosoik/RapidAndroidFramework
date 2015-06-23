package com.soi.rapidandroidapp.modules;

import android.content.Context;

import com.soi.rapidandroidapp.BaseApplication;
import com.soi.rapidandroidapp.api.managers.ApiManager;
import com.soi.rapidandroidapp.api.managers.FoursquareApiManager;
import com.soi.rapidandroidapp.events.common.BusProvider;
import com.soi.rapidandroidapp.managers.EnvironmentManager;
import com.soi.rapidandroidapp.managers.LoggerWrapper;
import com.soi.rapidandroidapp.managers.NetworkManager;
import com.soi.rapidandroidapp.managers.SessionManager;
import com.soi.rapidandroidapp.ui.LoginActivity;
import com.soi.rapidandroidapp.ui.MainActivity;
import com.soi.rapidandroidapp.ui.common.AbstractActivity;
import com.soi.rapidandroidapp.ui.common.AbstractFragmentActivity;
import com.soi.rapidandroidapp.ui.fragments.HomeFragment;
import com.soi.rapidandroidapp.ui.fragments.ItemActivity;
import com.soi.rapidandroidapp.ui.fragments.ItemViewFragment;
import com.soi.rapidandroidapp.utilities.DialogsHelper;
import com.soi.rapidandroidapp.utilities.Utils;
import com.squareup.otto.Bus;

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
                        AbstractActivity.class,
                        AbstractFragmentActivity.class,
                        MainActivity.class,
                        LoginActivity.class,
                        ItemActivity.class,
                        HomeFragment.class,
                        ItemViewFragment.class,
                        LoggerWrapper.class,
                        FoursquareApiManager.class,
                        ApiManager.class,
                },
                library = true
        )
public class AppModule {

    /**
     * Provides a SessionManager for injection based on context
     *
     * @param context
     * @return
     */
    @Provides
    SessionManager provideSessionManager(Context context)
    {
        return new SessionManager(context);
    }

    /**
     * Provides a singleton of FoursquareApiManager for injection
     *
     * @return
     */
    @Provides
    FoursquareApiManager provideFoursquareApiManager(Context context)
    {
        return new FoursquareApiManager(context);
    }

    /**
     * Provides a singleton of ApiManager for injection
     *
     * @return
     */
    @Provides
    ApiManager provideApiManager(Context context)
    {
        return new ApiManager(context);
    }

    /**
     * Provides a singleton of NetworkManager for injection based on context
     *
     * @param context
     * @return
     */
    @Provides
    @Singleton
    NetworkManager provideNetworkManager(Context context)
    {
        return NetworkManager.getInstance(context);
    }

    /**
     * Provides a singleton of EnvironmentManager for injection
     *
     * @return
     */
    @Provides
    @Singleton
    EnvironmentManager provideEnvironmentManager()
    {
        return EnvironmentManager.getInstance();
    }

    /**
     * Provides a singleton of Utilities for injection
     *
     * @return
     */
    @Provides
    @Singleton
    Utils provideUtilities()
    {

        return Utils.getInstance();
    }

    /**
     * Provides a singleton of Dialogs for injection
     *
     * @return
     */
    @Provides
    @Singleton
    DialogsHelper provideDialogs()
    {

        return DialogsHelper.getInstance();
    }

    /**
     * Provides a singleton of NetworkManager for injection based on context
     *
     * @param context
     * @return
     */
    @Provides
    @Singleton
    SocialManager provideSocialManager(Context context)
    {
        return SocialManager.getInstance(context);
    }

    /**
     * Returns a bus instance
     * @return
     */
    @Provides
    @Singleton
    Bus provideBusMain()
    {
        return BusProvider.getInstance();
    }
}
