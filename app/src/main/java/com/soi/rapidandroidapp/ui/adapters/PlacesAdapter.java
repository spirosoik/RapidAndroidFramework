package com.soi.rapidandroidapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.soi.rapidandroidapp.R;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common.Category;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common.Item;
import com.soi.rapidandroidapp.api.managers.net.response.foursquare.explore.common.Venue;
import com.soi.rapidandroidapp.utilities.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Spiros I. Oikonomakis on 1/29/15.
 */
public class PlacesAdapter extends BaseAdapter {

    private Context mContext;
    private List<Item> items;

    static class ViewHolder {
        @InjectView(R.id.txtName)
        TextView txtName;

        @InjectView(R.id.txtCategoryName)
        TextView txtCategoryName;

        @InjectView(R.id.imgPlace)
        ImageView imgPlace;

        public ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }
    }

    public PlacesAdapter(Context mContext, List<Item> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.item_venue, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Venue venue = items.get(position).venue;

        holder.txtName.setText(venue.name);

        if (venue.categories != null && venue.categories.size() > 0) {
            Category category = venue.categories.get(0);

            holder.txtCategoryName.setText(category.name);
            if (category.icon != null) {
                Picasso.with(mContext).load(StringUtils.join("", category.icon.prefix, "bg_88", category.icon.suffix))
                        .into(holder.imgPlace);
            }
        }

        return convertView;
    }
}