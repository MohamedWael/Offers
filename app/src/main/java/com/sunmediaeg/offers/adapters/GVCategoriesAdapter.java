package com.sunmediaeg.offers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.dataModel.Category;
import com.sunmediaeg.offers.utilities.Log;

import java.util.ArrayList;

/**
 * Created by Nourhan on 1/28/2017.
 */

public class GVCategoriesAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Category> categories;
    private Intent intent;

    public GVCategoriesAdapter(Context mContext, ArrayList<Category> categories) {
        this.mContext = mContext;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return categories.get(i).getIntentID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Category item = categories.get(i);

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.category_item_layout, viewGroup, false);
            RelativeLayout llMainRowItem = (RelativeLayout) view.findViewById(R.id.llMainRowItem);
            ImageButton ibTileImage = (ImageButton) view.findViewById(R.id.ibTileImage);
            TextView tvTileTitle = (TextView) view.findViewById(R.id.tvTileTitle);
            GridViewHolder viewHolder = new GridViewHolder(llMainRowItem, ibTileImage, tvTileTitle);
            view.setTag(viewHolder);
        }

        final GridViewHolder viewHolder = (GridViewHolder) view.getTag();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick", "view");
            }
        });

        viewHolder.tvTileTitle.setText(item.getTileTitle());
        viewHolder.ibTileImage.setImageResource(item.getTileImage());


        return view;
    }


    private class GridViewHolder implements View.OnClickListener, View.OnTouchListener {

        final private RelativeLayout llMainRowItem;
        final private ImageButton ibTileImage;
        final private TextView tvTileTitle;

        public GridViewHolder(RelativeLayout llMainRowItem, ImageButton ibTileImage, TextView tvTileTitle) {
            this.llMainRowItem = llMainRowItem;
            this.ibTileImage = ibTileImage;
            this.tvTileTitle = tvTileTitle;
            ibTileImage.setOnClickListener(this);
            ibTileImage.setOnTouchListener(this);


        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN ||
                    motionEvent.getAction() == MotionEvent.ACTION_HOVER_ENTER ||
                    motionEvent.getAction() == MotionEvent.ACTION_HOVER_MOVE ||
                    motionEvent.getAction() == MotionEvent.ACTION_BUTTON_PRESS ||
                    motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                GridViewHolder.this.llMainRowItem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                GridViewHolder.this.tvTileTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorBackground));
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP ||
                    motionEvent.getAction() == MotionEvent.ACTION_HOVER_EXIT ||
                    motionEvent.getAction() == MotionEvent.ACTION_BUTTON_RELEASE ||
                    motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE ||
                    motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                GridViewHolder.this.llMainRowItem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorBackground));
                GridViewHolder.this.tvTileTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
            } else {
                GridViewHolder.this.llMainRowItem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorBackground));
                GridViewHolder.this.tvTileTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
            }

            return false;
        }
    }

}
