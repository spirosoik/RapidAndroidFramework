package com.soi.rapidandroidapp.ui.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.soi.rapidandroidapp.R;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common.Item;
import com.soi.rapidandroidapp.ui.common.AbstractActivity;
import com.soi.rapidandroidapp.ui.common.AbstractFragmentActivity;

import java.util.HashMap;

public class ItemActivity extends AbstractFragmentActivity {

    public static final String KEY_ITEM = "KEY_ITEM";

    @Override
    public void initListeners() {

    }

    @Override
    protected void selectItem(int position) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle  = getIntent().getExtras();

        if (bundle.containsKey(KEY_ITEM)) {

            Item item = (Item)bundle.get(KEY_ITEM);
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put(KEY_ITEM, item);
            createFragment(ItemViewFragment.newInstance(data), HomeFragment.class.getName(), false, true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
