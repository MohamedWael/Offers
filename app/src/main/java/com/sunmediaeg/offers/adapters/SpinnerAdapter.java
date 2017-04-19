package com.sunmediaeg.offers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.dataModel.cities.City;

import java.util.ArrayList;

/**
 * Created by moham on 4/9/2017.
 */

public class SpinnerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<City> cities;

    public SpinnerAdapter(Context context, ArrayList<City> cities) {
        this.context = context;
        this.cities = cities;
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.spinner_item, parent);
        return convertView;
    }
}
