package com.soi.rapidandroidapp.ui.common;



import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.soi.rapidandroidapp.BaseApplication;
import com.soi.rapidandroidapp.R;
import com.soi.rapidandroidapp.managers.AnalyticsManager;
import com.soi.rapidandroidapp.managers.EnvironmentManager;
import com.soi.rapidandroidapp.modules.Injector;
import com.soi.rapidandroidapp.ui.adapters.NavigationDrawerAdapter;
import com.soi.rapidandroidapp.utilities.DialogsHelper;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public abstract class AbstractFragmentActivity extends ActionBarActivity {

    //Views
    @Optional
    @InjectView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;

    @Optional
    @InjectView(R.id.left_drawer)
    protected RecyclerView drawerList;

    protected ActionBarDrawerToggle actionBarDrawerToggle;

    //Configuration
    @Inject
    protected EnvironmentManager environmentManager;

    @Inject
    protected DialogsHelper dialogsHelper;

    /**
     * An array with all navigation drawer items
     */
    private String[] navigationDrawerItems;

    /**
     * If this is true then we set up a navigation drawer with menu
     */
    protected boolean hasNavigationDrawer;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (hasNavigationDrawer) {
            setContentView(R.layout.activity_navigation_drawer);
        } else {
            setContentView(R.layout.activity_fragment);
        }

        initActionBar();

        Injector.inject(this);
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
    }

    @Override
    protected void onPause()
    {
        super.onPause();

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
            boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
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
    protected void initNavigationDrawer(String[] items, int icDrawer, int sOpen, int sClose)
    {
        if (drawerLayout != null) {
            drawerList.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            drawerList.setLayoutManager(layoutManager);
            drawerList.setAdapter(new NavigationDrawerAdapter(items, this));

            final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            drawerList.addOnItemTouchListener(new NavigationItemClickListener());
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, icDrawer, sOpen, sClose) {
                public void onDrawerClosed(View view) {
                    getSupportActionBar().setTitle(actionBarTitle);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }

                public void onDrawerOpened(View drawerView) {
                    getSupportActionBar().setTitle(actionBarTitle);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
            };
            drawerLayout.setDrawerListener(actionBarDrawerToggle);
        }
    }
    private class NavigationItemClickListener implements RecyclerView.OnItemTouchListener {

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
            if(child!=null){
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


    protected void createFragment(Fragment targetFragment)
    {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment, targetFragment, "fragment")
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
        if (waitingDialog.isShowing())
            waitingDialog.dismiss();
    }
}