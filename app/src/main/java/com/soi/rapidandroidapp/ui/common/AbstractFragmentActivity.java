package com.soi.rapidandroidapp.ui.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.ViewUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
import com.soi.rapidandroidapp.managers.SessionManager;
import com.soi.rapidandroidapp.modules.Injector;
import com.soi.rapidandroidapp.ui.adapters.NavigationDrawerAdapter;
import com.soi.rapidandroidapp.ui.dialog.ErrorDialogFragment;
import com.soi.rapidandroidapp.utilities.DialogsHelper;
import com.soi.rapidandroidapp.utilities.StringUtils;
import com.soi.rapidandroidapp.utilities.Utils;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public abstract class AbstractFragmentActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    //Views
    @Optional
    @InjectView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;

    @Optional
    @InjectView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;

    @Optional
    @InjectView(R.id.left_drawer)
    protected RecyclerView drawerList;

    protected ActionBarDrawerToggle actionBarDrawerToggle;

    protected int fullBackground;

    //Configuration
    @Inject
    protected EnvironmentManager environmentManager;

    @Inject
    protected DialogsHelper dialogsHelper;

    @Inject
    protected SessionManager sessionManager;

    @Inject
    protected Utils utils;

    // Obtain same Singleton eventBus
    private Bus apiErrorBus = BusProvider.getInstance();

    private ApiErrorHandler apiErrorHandler;

    /**
     * Request code to use when launching the resolution activity
     */
    private static final int REQUEST_RESOLVE_ERROR = 1001;

    /**
     * Unique tag for the error dialog fragment
     */
    private static final String DIALOG_ERROR = "dialog_error";

    /**
     * Bool to track whether the app is already resolving an error
     */
    private boolean mResolvingError = false;

    /**
     * True when drawer is open
     */
    protected boolean isDrawerOpen;

    /**
     * Enable play services to connect
     */
    protected boolean usePlayServices = true;

    /**
     * Google api client for play services
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Last geolocation
     */
    protected Location mLastLocation;

    /**
     * Current geolocation latitude,longitude
     */
    protected Location mCurrentLocation;

    /**
     * Android location request to google play services
     */
    protected LocationRequest mLocationRequest;

    /**
     * An array with all navigation drawer items
     */
    private String[] navigationDrawerItems;

    /**
     * If this is true then we set up a navigation drawer with menu
     */
    protected boolean hasNavigationDrawer;

    /**
     * If this is true then we set up a navigation drawer with menu
     */
    protected boolean containsFragment = true;

    /**
     * *
     */
    protected int customLayout;

    /**
     * The current action bar of the screen if it's
     * exist
     */
    protected ActionBar actionBar;

    /**
     * The title of the actionbar if it's exist
     */
    protected String actionBarTitle;

    /**
     * The screen name you want to track in google analytics
     * or logging
     */
    protected String SCREEN_NAME = null;

    /**
     * Waiting dialog to show before go to next screen
     */
    private Dialog waitingDialog;

    /**
     * Init all the listeners for each view
     */
    public abstract void initListeners();

    /**
     * Select Items when have a navigation drawer and you
     * want to change in fragments
     */
    protected abstract void selectItem(int position);

    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (hasNavigationDrawer) {
            setContentView(R.layout.activity_navigation_drawer);
        } else if (containsFragment) {
            setContentView(R.layout.activity_fragment);
        } else {
            setContentView(customLayout);
        }

        if (fullBackground != 0 && containsFragment) {
            fragmentContainer.setBackgroundResource(fullBackground);
        }

        initActionBar();

        Injector.inject(this);
        apiErrorHandler = new ApiErrorHandler();

        // Enable google api client
        if (usePlayServices) {
            if (mGoogleApiClient == null) {
                buildGoogleApiClient();
            }
        }

        // Track Analytics
        if (environmentManager.canTrackGA()) {
            AnalyticsManager.getInstance().initTracker(this, BaseApplication.TrackerName.APP_TRACKER);

            if (SCREEN_NAME != null)
                AnalyticsManager.getInstance().trackScreenView(SCREEN_NAME);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (usePlayServices) {
            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (usePlayServices) {
            if (mGoogleApiClient != null) {
                mGoogleApiClient.disconnect();
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        BusProvider.getInstance().register(this);
        apiErrorBus.register(apiErrorHandler);

        if (usePlayServices && mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        BusProvider.getInstance().unregister(this);
        apiErrorBus.unregister(apiErrorHandler);

        if (usePlayServices) {
            stopLocationUpdates();
        }

    }

    @Override
    public void setContentView(int layoutResId)
    {
        super.setContentView(layoutResId);

        ButterKnife.inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (hasNavigationDrawer && actionBarDrawerToggle != null
                && actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title)
    {
        super.setTitle(title);

        if (getSupportActionBar() != null) {
            this.actionBar = getSupportActionBar();
            this.actionBar.setTitle(actionBarTitle);

        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        if (hasNavigationDrawer && actionBarDrawerToggle != null)
            actionBarDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        if (hasNavigationDrawer && actionBarDrawerToggle != null)
            actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (hasNavigationDrawer && drawerLayout != null && drawerList != null) {
            // If the nav drawer is open, hide action items related to the content view
            isDrawerOpen = drawerLayout.isDrawerOpen(drawerList);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Initializes action bar if it exists with some defaults
     */
    protected void initActionBar()
    {

        this.actionBar = getSupportActionBar();

        if (this.actionBar != null && this.actionBar.isShowing() && hasNavigationDrawer) {
            this.actionBar.setDisplayHomeAsUpEnabled(true);
            this.actionBar.setDisplayShowHomeEnabled(true);
            this.actionBar.setDisplayUseLogoEnabled(true);

        }
    }

    /**
     * Create a navigation drawer
     * @param items the navigation items
     * @param icDrawer the drawer icon at the top
     * @param sOpen the string title when the drawer opens
     * @param sClose the string title when the drawer closes
     */
    protected void initNavigationDrawer(String[] items, String[] subtitles, Integer[] icons,
                                        int icDrawer, int sOpen, int sClose)
    {
        if (drawerLayout != null) {
            drawerList.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            drawerList.setLayoutManager(layoutManager);
            drawerList.setAdapter(new NavigationDrawerAdapter(this, items, subtitles, icons));

            mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            drawerList.addOnItemTouchListener(new NavigationItemClickListener());
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, icDrawer, sOpen, sClose) {
                public void onDrawerClosed(View view) {
                    getSupportActionBar().setTitle(actionBarTitle);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                    actionBar.setTitle(getString(R.string.app_name));
                }

                public void onDrawerOpened(View drawerView) {
                    getSupportActionBar().setTitle(actionBarTitle);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                    actionBar.setTitle("Menu");
                }
            };
            drawerLayout.setDrawerListener(actionBarDrawerToggle);
        }
    }

    private class NavigationItemClickListener implements RecyclerView.OnItemTouchListener {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
            if(child!=null && mGestureDetector.onTouchEvent(motionEvent)){
                drawerLayout.closeDrawers();
                selectItem(recyclerView.getChildPosition(child));
                return true;
            }
            return false;
        }
        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }
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
     * Just shows a waiting dialog
     * @param title
     * @param text
     */
    protected void showWaitingDialog(String title, String text)
    {
        waitingDialog = dialogsHelper.getCustomDialog(this, title, text);
        waitingDialog.show();
    }

    /**
     * Just dismisses a waiting dialog
     */
    protected void dismissWaitingDialog()
    {
        if (waitingDialog != null && waitingDialog.isShowing())
            waitingDialog.dismiss();
    }

    /**
     * Create a location request with necessary configs
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Start location updates periodically
     */
    protected void startLocationUpdates() {
        if (usePlayServices && mGoogleApiClient.isConnected())
            if (LocationServices.FusedLocationApi != null) {
                if (mLocationRequest == null) {
                    createLocationRequest();
                }

                if (mGoogleApiClient == null) {
                    buildGoogleApiClient();
                }
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
    }
    /**
     * Stop the location updates periodically
     */
    protected void stopLocationUpdates() {
        if (usePlayServices && mGoogleApiClient.isConnected())
            if (LocationServices.FusedLocationApi != null) {

                if (mLocationRequest == null) {
                    createLocationRequest();
                }

                if (mGoogleApiClient == null) {
                    buildGoogleApiClient();
                }
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }
    }

    /**
     * Google api client to connect to location services API
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    // Location updates from LocationListener (GOOGLE PLAY SERVICES)
    @Override
    public void onLocationChanged(Location location) {
        if (usePlayServices)
            mCurrentLocation = location;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (usePlayServices) {
            if (LocationServices.FusedLocationApi != null) {
                if (mGoogleApiClient == null) {
                    buildGoogleApiClient();
                }
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                // save current location
                if (mLastLocation != null) {
                    sessionManager.setCurrentLocation(StringUtils.join(",", mLastLocation.getLatitude(),
                            mLastLocation.getLongitude()));
                }
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (connectionResult.hasResolution()) {
            try {
                mResolvingError = true;
                connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            // Show dialog using GooglePlayServicesUtil.getErrorDialog()
            showErrorDialog(connectionResult.getErrorCode());
            mResolvingError = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            mResolvingError = false;
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mGoogleApiClient.isConnecting() && !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * Creates a dialog for an error message
     * @param errorCode
     */
    private void showErrorDialog(int errorCode) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(ErrorDialogFragment.KEY_DIALOG_ERROR, errorCode);
        data.put(ErrorDialogFragment.KEY_REQUEST_RESOLVE_ERROR, REQUEST_RESOLVE_ERROR);
        ErrorDialogFragment dialogFragment = ErrorDialogFragment.newInstance(data);

        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "errordialog");
    }

    private class ApiErrorHandler {
        // API ERROR HANDLING
        @Subscribe
        public void onHttpUnauthorized(HttpUnauthorizedEvent event) {
            dismissWaitingDialog();
            Toast.makeText(AbstractFragmentActivity.this, getString(R.string.txt_unauthorized_error), Toast.LENGTH_SHORT).show();
        }

        @Subscribe
        public void onHttpServerError(HttpServerErrorEvent event) {
            dismissWaitingDialog();
            Toast.makeText(AbstractFragmentActivity.this, getString(R.string.txt_server_error), Toast.LENGTH_SHORT).show();
        }

        @Subscribe
        public void onHttpNotFound(HttpNotfoundEvent event) {
            dismissWaitingDialog();
            Toast.makeText(AbstractFragmentActivity.this, getString(R.string.txt_notfound_error), Toast.LENGTH_SHORT).show();
        }

        @Subscribe
        public void onHttpUpgradeRequired(HttpUpgradeRequiredEvent event) {
            dismissWaitingDialog();
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AbstractFragmentActivity.this);
            alertDialog.setCancelable(false);
            alertDialog.setMessage(event.msg);
            alertDialog.setPositiveButton("Upgrade", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    utils.openURL(AbstractFragmentActivity.this, getString(R.string.app_url));
                }
            });
            alertDialog.show();
        }

        @Subscribe
        public void onNoInternetEvent(NotInternetEvent event) {
            dialogsHelper.toastMakeAndShow(AbstractFragmentActivity.this, "No internet connection", Toast.LENGTH_LONG);
        }
    }
}