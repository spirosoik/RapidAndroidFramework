package com.soi.rapidandroidapp.ui.common;


import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.soi.rapidandroidapp.events.common.BusProvider;

import butterknife.ButterKnife;

public class BaseFragmentActivity  extends FragmentActivity {
    public ActionBar actionBar;
    public String actionBarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Otto", "Eventbus registered");
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Otto", "Eventbus Unregistered");
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);

        ButterKnife.inject(this);
        if (actionBarTitle != null) {
            actionBar.setTitle(actionBarTitle);
        }
    }


    public void initActionBar()
    {
        actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
        }
    }
}