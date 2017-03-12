package com.sunmediaeg.offers.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.sunmediaeg.offers.activities.OffersGeneralActivity;
import com.sunmediaeg.offers.utilities.Constants;
import com.sunmediaeg.offers.views.TimerView;

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

        holder.ivProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offersGeneralActivityIntent = new Intent(mContext, OffersGeneralActivity.class);
                offersGeneralActivityIntent.putExtra(Constants.ACTIVITY, Constants.ACTIVITY_PRODUCT_DETAILS);
                mContext.startActivity(offersGeneralActivityIntent);
            }
        });
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
        public TimerView timerView;

        public OffersViewHolder(View itemView) {
            super(itemView);

            ibOfferCompanyLogo = (ImageButton) itemView.findViewById(R.id.ibOfferCompanyLogo);
            ibOfferCategoryImage = (ImageButton) itemView.findViewById(R.id.ibOfferCategoryImage);
            ivProductImage = (ImageView) itemView.findViewById(R.id.ivProductImage);
            tvOfferCompanyName = (TextView) itemView.findViewById(R.id.tvOfferCompanyName);
            tvOfferPrice = (TextView) itemView.findViewById(R.id.tvOfferPrice);
            tvOfferDescription = (TextView) itemView.findViewById(R.id.tvOfferDescription);

            timerView = (TimerView) itemView.findViewById(R.id.timerView);


            rgLike = (RadioGroup) itemView.findViewById(R.id.rgLike);
            rbLike = (RadioButton) itemView.findViewById(R.id.rbLike);
            rbDislike = (RadioButton) itemView.findViewById(R.id.rbDislike);
        }
    }
}
