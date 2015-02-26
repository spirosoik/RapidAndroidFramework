package com.soi.rapidandroidapp.ui.common;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.soi.rapidandroidapp.BaseApplication;
import com.soi.rapidandroidapp.R;
import com.soi.rapidandroidapp.api.managers.events.common.HttpNotfoundEvent;
import com.soi.rapidandroidapp.api.managers.events.common.HttpServerErrorEvent;
import com.soi.rapidandroidapp.api.managers.events.common.HttpUnauthorizedEvent;
import com.soi.rapidandroidapp.api.managers.events.common.HttpUpgradeRequiredEvent;
import com.soi.rapidandroidapp.api.managers.events.common.NotInternetEvent;
import com.soi.rapidandroidapp.events.common.BusProvider;
import com.soi.rapidandroidapp.managers.AnalyticsManager;
import com.soi.rapidandroidapp.managers.EnvironmentManager;
import com.soi.rapidandroidapp.modules.Injector;
import com.soi.rapidandroidapp.utilities.DialogsHelper;
import com.soi.rapidandroidapp.utilities.Utils;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class AbstractActivity extends ActionBarActivity {

    @Inject
    protected EnvironmentManager environmentManager;

    @Inject
    protected Utils utils;
    
    @Inject
    protected DialogsHelper dialogsHelper;

    // Obtain same Singleton eventBus
    private Bus apiErrorBus = BusProvider.getInstance();

    private ApiErrorHandler apiErrorHandler;

    /**
     * The current action bar of the screen if it's
     * exist
     */
    protected android.support.v7.app.ActionBar actionBar;

    /**
     * The title of the actionbar if it's exist
     */
    protected String actionBarTitle;

    /**
     * The screen name you want to track in google analytics
     * or logging
     */
    protected String SCREEN_NAME = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();

        Injector.inject(this);

        apiErrorHandler = new ApiErrorHandler();

        if (environmentManager.canTrackGA()) {
            AnalyticsManager.getInstance().initTracker(this, BaseApplication.TrackerName.APP_TRACKER);

            if (SCREEN_NAME != null)
                AnalyticsManager.getInstance().trackScreenView(SCREEN_NAME);
        }
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        BusProvider.getInstance().register(this);
        apiErrorBus.register(apiErrorHandler);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        BusProvider.getInstance().unregister(this);
        apiErrorBus.unregister(apiErrorHandler);
    }

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);

        ButterKnife.inject(this);
        if (this.actionBarTitle != null && this.actionBar != null) {
            this.actionBar.setTitle(actionBarTitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void createFragment(Fragment targetFragment, boolean mustAddToBackStack)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mustAddToBackStack) {
            transaction.addToBackStack(null);
        }
        targetFragment.setRetainInstance(true);
        transaction.replace(R.id.fragmentContainer, targetFragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    /**
     * Initializes action bar if it exists with some defaults
     */
    public void initActionBar() {

        this.actionBar = getSupportActionBar();

        if (this.actionBar != null && this.actionBar.isShowing()) {
            this.actionBar.setDisplayHomeAsUpEnabled(true);
            this.actionBar.setDisplayShowHomeEnabled(true);
            this.actionBar.setDisplayUseLogoEnabled(true);

        }
    }


    private class ApiErrorHandler {
        // API ERROR HANDLING
        @Subscribe
        public void onHttpUnauthorized(HttpUnauthorizedEvent event) {
            Toast.makeText(AbstractActivity.this, getString(R.string.txt_unauthorized_error), Toast.LENGTH_SHORT).show();
        }

        @Subscribe
        public void onHttpServerError(HttpServerErrorEvent event) {
            Toast.makeText(AbstractActivity.this, getString(R.string.txt_server_error), Toast.LENGTH_SHORT).show();
        }

        @Subscribe
        public void onHttpNotFound(HttpNotfoundEvent event) {
            Toast.makeText(AbstractActivity.this, getString(R.string.txt_notfound_error), Toast.LENGTH_SHORT).show();
        }

        @Subscribe
        public void onHttpUpgradeRequired(HttpUpgradeRequiredEvent event) {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getBaseContext());
            alertDialog.setCancelable(false);
            alertDialog.setMessage(event.msg);
            alertDialog.setPositiveButton("Upgrade", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    utils.openURL(AbstractActivity.this, getString(R.string.app_url));
                }
            });
            alertDialog.show();
        }
        @Subscribe
        public void onNoInternetEvent(NotInternetEvent event) {
            dialogsHelper.toastMakeAndShow(AbstractActivity.this, "No internet connection", Toast.LENGTH_LONG);
        }
    }
}
