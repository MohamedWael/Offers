package com.mowael.offers.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mowael.offers.R;
import com.mowael.offers.dataModel.cities.City;

import java.util.List;

/**
 * Created by moham on 9/2/2017.
 */

public class CitiesAdapter extends ArrayAdapter<City>{


    public CitiesAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        City city= getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }
        // Lookup view for data population

        TextView tvCity = (TextView) convertView;//convertView.findViewById(R.id.tvSpinner);
        // Populate the data into the template view using the data object

        if (city != null) {
            tvCity.setText(city.getName());
        }

        // Return the completed view to render on screen
        return convertView;
    }

}
