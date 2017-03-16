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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;
import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.activities.OffersGeneralActivity;
import com.sunmediaeg.offers.dataModel.myOffersResponse.Feed;
import com.sunmediaeg.offers.utilities.Constants;
import com.sunmediaeg.offers.utilities.Logger;
import com.sunmediaeg.offers.utilities.VolleySingleton;
import com.sunmediaeg.offers.views.TimerView;
import com.sunmediaeg.offers.views.TimerViewCounter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by moham on 1/26/2017.
 */

public class RVOffersAdapter extends RecyclerView.Adapter<RVOffersAdapter.OffersViewHolder> {

    private Context mContext;
    private RealmList<Feed> feeds;
    private SimpleDateFormat dateFormat;

    public RVOffersAdapter(Context mContext, RealmList<Feed> feeds) {
        this.mContext = mContext;
        this.feeds = feeds;
        dateFormat = new SimpleDateFormat(TimerViewCounter.STANDARD_TIME_FORMAT);
    }

    @Override
    public OffersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.offer_item_layout, parent, false);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(view);
        return new OffersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OffersViewHolder holder, int position) {
        final Feed feed = feeds.get(position);
        holder.tvOfferDescription.setText(feed.getShortDescription());
        Picasso.with(mContext).load(VolleySingleton.getInstance(mContext).uriEncoder(feed.getImage())).placeholder(R.drawable.photo_replacement).into(holder.ivProductImage);

        holder.timerView.setTime(feed.getStartDate(), feed.getEndDate(), new TimerViewCounter.RemainingTime() {
            @Override
            public void onTimeOut() {
                Logger.d("FeedTime", feed.getTitle()+" TimeOut");
            }

            @Override
            public void totalPeriod(long totalPeriod) {

            }

            @Override
            public void getTime(long remainingTime) {

            }
        });

        holder.tvOfferPrice.setText(feed.getPrice());
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
        return feeds.size();
    }

    class OffersViewHolder extends RecyclerView.ViewHolder {

        public ImageButton ibOfferCompanyLogo, ibOfferCategoryImage;
        public TextView tvOfferCompanyName, tvOfferPrice, tvOfferDescription;
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
