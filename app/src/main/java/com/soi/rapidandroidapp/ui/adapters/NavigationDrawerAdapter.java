package com.soi.rapidandroidapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private String[] navigationItemsSubtitle;
    private Integer[] navigationItemIcons;
    private OnNavItemClickListener mListener;

    /**
     * Interface for receiving click events from cells.
     */
    public interface OnNavItemClickListener {
        public void onNavigationClick(View view, int position);
    }

    public NavigationDrawerAdapter(Context ctx, String[] navigationItems,
                                   String[] navigationItemsSubtitle, Integer[] navigationItemIcons) {
        this.ctx = ctx;
        this.navigationItems = navigationItems;
        this.navigationItemsSubtitle = navigationItemsSubtitle;
        this.navigationItemIcons = navigationItemIcons;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View root;

        @InjectView(R.id.txtMenuTitle)
        TextView txtMenuTitle;

        @InjectView(R.id.txtMenuSubtitle)
        TextView txtMenuSubtitle;

        @InjectView(R.id.imgIcon)
        ImageView imgIcon;

        public ViewHolder(View v) {
            super(v);

            root = v;
            ButterKnife.inject(this, v);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (navigationItems != null && navigationItems.length > 0) {
            viewHolder.txtMenuTitle.setText(navigationItems[position]);
            viewHolder.txtMenuSubtitle.setText(navigationItemsSubtitle[position]);
            viewHolder.imgIcon.setImageDrawable(ctx.getResources().getDrawable(navigationItemIcons[position]));
        }
    }

    @Override
    public int getItemCount() {
        return navigationItems.length;
    }
}
