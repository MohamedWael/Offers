package com.sunmediaeg.offers.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sunmediaeg.offers.R;

/**
 * Created by moham on 1/26/2017.
 */

public class RVOffersAdapter extends RecyclerView.Adapter<RVOffersAdapter.OffersViewHolder> {

    Context mContext;

    public RVOffersAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public OffersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.offer_item_layout, parent, false);
        return new OffersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OffersViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class OffersViewHolder extends RecyclerView.ViewHolder {

        public ImageButton ibOfferCompanyLogo, ibOfferCategoryImage;
        public TextView tvOfferCompanyName, tvOfferPrice, tvOfferDescription, tvRemainingDate, tvDay, tvHour, tvSecond, tvMinuit;
        public ImageView ivProductImage;
        public RadioGroup rgLike;
        public RadioButton rbLike, rbDislike;

        public OffersViewHolder(View itemView) {
            super(itemView);

            ibOfferCompanyLogo = (ImageButton) itemView.findViewById(R.id.ibOfferCompanyLogo);
            ibOfferCategoryImage = (ImageButton) itemView.findViewById(R.id.ibOfferCategoryImage);
            ivProductImage = (ImageView) itemView.findViewById(R.id.ivProductImage);
            tvOfferCompanyName = (TextView) itemView.findViewById(R.id.tvOfferCompanyName);
            tvOfferPrice = (TextView) itemView.findViewById(R.id.tvOfferPrice);
            tvOfferDescription = (TextView) itemView.findViewById(R.id.tvOfferDescription);
            tvRemainingDate = (TextView) itemView.findViewById(R.id.tvRemainingDate);
            tvHour = (TextView) itemView.findViewById(R.id.tvHour);
            tvSecond = (TextView) itemView.findViewById(R.id.tvSecond);
            tvMinuit = (TextView) itemView.findViewById(R.id.tvMinuit);
            rgLike = (RadioGroup) itemView.findViewById(R.id.rgLike);
            rbLike = (RadioButton) itemView.findViewById(R.id.rbLike);
            rbDislike = (RadioButton) itemView.findViewById(R.id.rbDislike);
        }
    }
}
