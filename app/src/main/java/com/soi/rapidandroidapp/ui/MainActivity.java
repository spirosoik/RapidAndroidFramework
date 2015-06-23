package com.soi.rapidandroidapp.ui;

import android.os.Bundle;

import com.soi.rapidandroidapp.ui.common.AbstractFragmentActivity;
import com.soi.rapidandroidapp.ui.fragments.HomeFragment;

public class MainActivity extends AbstractFragmentActivity {

    @Override
    public void initListeners() {

    }

    @Override
    protected void selectItem(int position) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createFragment(HomeFragment.newInstance(null), HomeFragment.class.getName(), true, false);
    }
}
