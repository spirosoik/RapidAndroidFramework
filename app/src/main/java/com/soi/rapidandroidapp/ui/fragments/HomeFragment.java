package com.soi.rapidandroidapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.soi.rapidandroidapp.R;
import com.soi.rapidandroidapp.api.managers.ApiManager;
import com.soi.rapidandroidapp.api.managers.FoursquareApiManager;
import com.soi.rapidandroidapp.api.managers.events.FoursquareExploreEvent;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common.Item;
import com.soi.rapidandroidapp.modules.Injector;
import com.soi.rapidandroidapp.ui.adapters.PlacesAdapter;
import com.soi.rapidandroidapp.ui.common.BaseFragment;
import com.soi.rapidandroidapp.utilities.Constants;
import com.squareup.otto.Subscribe;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    @InjectView(R.id.txtWelcomeMsg)
    TextView mTxtWelcomeMsg;

    @InjectView(R.id.lvPlaces)
    ListView lvPlaces;

    @Inject
    Provider<FoursquareApiManager> foursquareApiManagerProvider;

    private PlacesAdapter mPlacesAdapter;

    public static HomeFragment newInstance(HashMap<String, Object> params) {
        HomeFragment fragment = new HomeFragment();

        Bundle args = new Bundle();
        if (params != null) {
            for (String key : params.keySet()) {
                args.putSerializable(key, (Serializable) params.get(key));
            }
            fragment.setArguments(args);
        }
        return fragment;
    }


    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Injector.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.inject(this, view);

        mTxtWelcomeMsg.setText("HELLOOOOOO FOURSQUARE");

        if (foursquareApiManagerProvider != null && foursquareApiManagerProvider.get() != null) {
            String ll = "40.7,-74";
            String clientId = Constants.FOURSQUARE_API_CLIENT_ID;
            String clientSecret = Constants.FOURSQUARE_API_CLIENT_SECRET;
            int limit = 15;
            int sortByDistance = 1;
            String v = FoursquareApiManager.dateFormatter.format(new Date());

            if (foursquareApiManagerProvider.get() != null) {
                foursquareApiManagerProvider.get().explore(ll, clientId, clientSecret, limit, sortByDistance, v);
            }
        }

        return view;
    }

    @Subscribe
    public void onFoursquareExploreEvent(FoursquareExploreEvent event)
    {
        if (event.getResponse() != null) {

            if (event.getResponse().response != null &&  event.getResponse().response.groups != null) {
                mPlacesAdapter = new PlacesAdapter(getActivity(), event.getResponse().response.groups.get(0).items);
                lvPlaces.setAdapter(mPlacesAdapter);
                lvPlaces.setOnItemClickListener(this);
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = (Item) mPlacesAdapter.getItem(position);
        Intent intent = new Intent(getActivity(), ItemActivity.class);
        intent.putExtra(ItemActivity.KEY_ITEM, item);
        startActivity(intent);
    }
}