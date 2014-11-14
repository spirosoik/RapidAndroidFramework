package com.soi.rapidandroidapp.ui.common;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import butterknife.ButterKnife;

public class BaseActivity extends Activity {

    public ActionBar actionBar;
    public String actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        initActionBar();
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

    public void initActionBar() {

        this.actionBar = getActionBar();

        if (this.actionBar != null && this.actionBar.isShowing()) {
            this.actionBar.setDisplayHomeAsUpEnabled(true);
            this.actionBar.setDisplayShowHomeEnabled(true);
            this.actionBar.setDisplayUseLogoEnabled(true);

        }
    }
}
