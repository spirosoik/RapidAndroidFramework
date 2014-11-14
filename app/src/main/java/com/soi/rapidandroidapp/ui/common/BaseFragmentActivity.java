package com.soi.rapidandroidapp.ui.common;


import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;

public class BaseFragmentActivity extends Activity {
    public ActionBar actionBar;
    public String       actionBarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();

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