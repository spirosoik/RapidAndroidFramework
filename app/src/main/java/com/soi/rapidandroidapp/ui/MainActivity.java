package com.soi.rapidandroidapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.soi.rapidandroidapp.ui.common.AbstractFragmentActivity;
import com.soi.rapidandroidapp.ui.common.BaseFragment;
import com.soi.rapidandroidapp.ui.fragments.HomeFragment;

public class MainActivity extends AbstractFragmentActivity {

    /**
     * This si the fragment which is now into the fragment container
     * and it's shown up to the user
     */
    private BaseFragment currentFragment;

    @Override
    public void initListeners() {

    }

    @Override
    protected void selectItem(int position) {

    }

    @Override
    public void onBackPressed() {
        if (currentFragment.allowBackPressed()) {
            currentFragment.onBackPressed();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentFragment = HomeFragment.newInstance(null);
        createFragment(currentFragment, HomeFragment.class.getName(), true, false);
    }
}
