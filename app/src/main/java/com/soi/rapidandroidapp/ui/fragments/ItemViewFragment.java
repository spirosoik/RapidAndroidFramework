package com.soi.rapidandroidapp.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soi.rapidandroidapp.R;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common.Item;
import com.soi.rapidandroidapp.ui.common.BaseFragment;

import java.io.Serializable;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ItemViewFragment extends BaseFragment {

    @InjectView(R.id.txtVenueName)
    TextView txtVenueName;

    @InjectView(R.id.txtCategoryName)
    TextView txtCategoryName;

    @InjectView(R.id.txtAddress)
    TextView txtAddress;


    public static ItemViewFragment newInstance(HashMap<String, Object> params)
    {
        ItemViewFragment fragment = new ItemViewFragment();
        if (params != null && params.size() > 0) {

            Bundle args = new Bundle();
            for (String key : params.keySet()) {
                args.putSerializable(key, (Serializable) params.get(key));
            }
            fragment.setArguments(args);
        }
        return fragment;
    }

    public ItemViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View parentView = inflater.inflate(R.layout.fragment_item_view, container, false);
        ButterKnife.inject(this, parentView);

        Bundle bundle = getArguments();
        if (bundle.containsKey(ItemActivity.KEY_ITEM)) {
            Item item = (Item) bundle.get(ItemActivity.KEY_ITEM);

            if (item.venue != null) {
                txtVenueName.setText(item.venue.name);
                if (item.venue.categories != null && item.venue.categories.size() > 0) {
                    txtCategoryName.setText(item.venue.categories.get(0).name);
                }
                if (item.venue.location != null) {
                    txtAddress.setText(item.venue.location.address);
                }
            }
        }
        return parentView;
    }

}
