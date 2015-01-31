package com.soi.rapidandroidapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.soi.rapidandroidapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Spiros I. Oikonomakis on 1/27/15.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder>
{
    private Context ctx;
    private String[] navigationItems;
    private OnNavItemClickListener mListener;

    /**
     * Interface for receiving click events from cells.
     */
    public interface OnNavItemClickListener {
        public void onNavigationClick(View view, int position);
    }

    public NavigationDrawerAdapter(String[] navigationItems, Context ctx) {
        this.navigationItems = navigationItems;
        this.ctx = ctx;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View root;

        @InjectView(R.id.navigationContainer)
        LinearLayout navigationContainer;

        @InjectView(R.id.navigationItem)
        TextView navigationItem;

        public ViewHolder(View v) {
            super(v);

            root = v;
            ButterKnife.inject(this, v);

        }
    }

    @Override
    public NavigationDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (navigationItems != null && navigationItems.length > 0) {
            viewHolder.navigationItem.setText(navigationItems[position]);
        }
    }

    @Override
    public int getItemCount() {
        return navigationItems.length;
    }
}
