package com.mowael.offers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.mowael.offers.R;
import com.mowael.offers.activities.OffersGeneralActivity;
import com.mowael.offers.dataModel.categories.Category;
import com.mowael.offers.utilities.Constants;
import com.mowael.offers.utilities.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nourhan on 1/28/2017.
 */

public class GVCategoriesAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Category> categories;
    private boolean isCategoriesFragment = true;
    private Intent intent;

    public GVCategoriesAdapter(Context mContext, ArrayList<Category> categories) {
        this.mContext = mContext;
        this.categories = categories;
    }

    public GVCategoriesAdapter(Context mContext, ArrayList<Category> categories, boolean isCategoriesFragment) {
        this.mContext = mContext;
        this.categories = categories;
        this.isCategoriesFragment = isCategoriesFragment;
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
        return categories.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Category item = categories.get(i);

        if (view == null) {
            if (isCategoriesFragment) {
                view = LayoutInflater.from(mContext).inflate(R.layout.category_item_layout, viewGroup, false);
            } else {
                view = LayoutInflater.from(mContext).inflate(R.layout.category_item_layout_wide, viewGroup, false);
            }
            RelativeLayout llMainRowItem = (RelativeLayout) view.findViewById(R.id.llMainRowItem);
            ImageButton ibTileImage = (ImageButton) view.findViewById(R.id.ibTileImage);
            TextView tvTileTitle = (TextView) view.findViewById(R.id.tvTileTitle);
            GridViewHolder viewHolder = new GridViewHolder(llMainRowItem, ibTileImage, tvTileTitle);
            view.setTag(viewHolder);
        }

        final GridViewHolder viewHolder = (GridViewHolder) view.getTag();
        viewHolder.setCategoryID(item.getId());
        viewHolder.tvTileTitle.setText(item.getName());
        Picasso.with(mContext).load(item.getImage()).placeholder(R.drawable.logo).into(viewHolder.ibTileImage);
        return view;
    }


    private class GridViewHolder implements View.OnClickListener, View.OnTouchListener {

        final private RelativeLayout llMainRowItem;
        final private ImageButton ibTileImage;
        final private TextView tvTileTitle;
        private int categoryID = -1;

        public GridViewHolder(RelativeLayout llMainRowItem, ImageButton ibTileImage, TextView tvTileTitle) {
            this.llMainRowItem = llMainRowItem;
            this.ibTileImage = ibTileImage;
            this.tvTileTitle = tvTileTitle;
            ibTileImage.setOnClickListener(this);
            llMainRowItem.setOnClickListener(this);
            tvTileTitle.setOnClickListener(this);
            ibTileImage.setOnTouchListener(this);
            llMainRowItem.setOnTouchListener(this);
            tvTileTitle.setOnTouchListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, OffersGeneralActivity.class);
            intent.putExtra(Constants.ACTIVITY, Constants.ACTIVITY_CATEGORY_COMPANIES);
            intent.putExtra(Constants.CATEGORY_TITLE, tvTileTitle.getText().toString());
            Logger.d(Constants.CATEGORY_ID, categoryID + "");
            intent.putExtra(Constants.CATEGORY_ID, categoryID);
            mContext.startActivity(intent);
        }

        public void setCategoryID(int categoryID) {
            this.categoryID = categoryID;
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
