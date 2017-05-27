package com.mowael.offers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.mowael.offers.R;
import com.mowael.offers.activities.MainActivity;
import com.mowael.offers.activities.OffersGeneralActivity;
import com.mowael.offers.dataModel.APIResponse;
import com.mowael.offers.dataModel.myOffersResponse.Feed;
import com.mowael.offers.utilities.CacheManager;
import com.mowael.offers.utilities.Constants;
import com.mowael.offers.utilities.Logger;
import com.mowael.offers.utilities.Service;
import com.mowael.offers.utilities.VolleySingleton;
import com.mowael.offers.views.TimerView;
import com.mowael.offers.views.TimerViewCounter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Iterator;

import io.realm.RealmList;

//import com.sunmediaeg.offers.utilities.RealmDB;

/**
 * Created by moham on 1/26/2017.
 */

public class RVOffersAdapter extends RecyclerView.Adapter<RVOffersAdapter.OffersViewHolder> {

    private Context mContext;
    private RealmList<Feed> feeds;
    private SimpleDateFormat dateFormat;

    public RVOffersAdapter(Context mContext, RealmList<Feed> feeds) {
        this.mContext = mContext;
//        this.feeds = filterOutDatedFeeds(feeds);
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
    public void onBindViewHolder(final OffersViewHolder holder, final int position) {
        final Feed feed = feeds.get(position);
        boolean setFeed = feed.getEndDate() > System.currentTimeMillis();
        Logger.d("setFeed", setFeed + "");
//        RealmDB.getInstance(mContext).createOrUpdate(feed);
        holder.timerView.setTime(feed.getStartDate(), feed.getEndDate(), new TimerViewCounter.RemainingTime() {
            @Override
            public void onTimeOut() {
                if (holder.getCurrentView() != null) {
//                    YoYo.with(Techniques.FadeOut)
//                            .duration(700)
//                            .playOn(holder.getCurrentView());
//                    holder.getCurrentView().setVisibility(View.GONE);
                }
//                Logger.d("FeedTime", feed.getTitle() + " TimeOut");
            }

            @Override
            public void totalPeriod(long totalPeriod) {

            }

            @Override
            public void getTime(long remainingTime) {

            }
        });

        holder.tvOfferDescription.setText(feed.getShortDescription());
        Picasso.with(mContext).load(VolleySingleton.getInstance(mContext).uriEncoder(feed.getImage())).placeholder(R.drawable.photo_replacement).into(holder.ivProductImage);
        holder.tvOfferPrice.setText(feed.getPrice());
        holder.tvOfferCompanyName.setText(feed.getVendorName());

        Picasso.with(mContext).load(VolleySingleton.getInstance(mContext).uriEncoder(feed.getCategoryImage())).placeholder(R.drawable.logo).into(holder.ibOfferCategoryImage);
        Picasso.with(mContext).load(VolleySingleton.getInstance(mContext).uriEncoder(feed.getVendorImage())).placeholder(R.drawable.logo).into(holder.ibOfferCompanyLogo);

        holder.ibOfferCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OffersGeneralActivity.class);
                intent.putExtra(Constants.ACTIVITY, Constants.ACTIVITY_CATEGORY_COMPANIES);
                intent.putExtra(Constants.CATEGORY_ID, feed.getCategoryId());
                mContext.startActivity(intent);
            }
        });

        holder.tvOfferCompanyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVendorProfile(feed);
            }
        });

        holder.ibOfferCompanyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVendorProfile(feed);
            }
        });

        holder.tvOfferPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetails(position, feed);
            }
        });

        holder.tvOfferDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetails(position, feed);
            }
        });

        holder.ivProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetails(position, feed);
            }
        });

        holder.timerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetails(position, feed);
            }
        });

        if (feed.isLiked()) {
            holder.rbLike.setChecked(true);
            holder.rbDislike.setChecked(false);
        } else if (feed.isFeedDisLiked()) {
            holder.rbDislike.setChecked(true);
            holder.rbLike.setChecked(false);
        } else {
            holder.rbLike.setChecked(false);
            holder.rbDislike.setChecked(false);
        }
        holder.rgLike.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {
                    case R.id.rbLike:
                        like(feed, true);
                        holder.rbLike.setChecked(true);
                        holder.rbDislike.setChecked(false);
                        break;
                    case R.id.rbDislike:
                        like(feed, false);
                        holder.rbDislike.setChecked(true);
                        holder.rbLike.setChecked(false);
                        break;
                }
            }
        });
    }

    private void openVendorProfile(Feed feed) {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra(Constants.IS_COMPANY_PROFILE, true);
        intent.putExtra(Constants.COMPANY_PROFILE_TITLE, feed.getVendorId() + "");
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (feeds == null) {
            Logger.d("RVOffersAdapter", "feeds is null");
            return 0;
        }
//        Logger.d("feedSize", feeds.size() + "");
        return feeds.size();
    }

    private void like(final Feed feed, boolean like) {
        try {
            long userID = (long) CacheManager.getInstance().getCachedObject(Constants.USER_ID);
            Logger.d(Constants.USER_ID, userID + "");
            String token = (String) CacheManager.getInstance().getCachedObject(Constants.TOKEN);
            Logger.d(Constants.TOKEN, token + "");
            if (!token.isEmpty() && userID != 0) {
                JSONObject body = new JSONObject();
                body.put(Constants.USER_ID, userID);
                body.put(Constants.TOKEN, token);
                body.put(Constants.OFFER_ID, feed.getId());
                Service.getInstance(mContext).getResponse(Request.Method.POST, like ? Constants.LIKE_OFFER : Constants.DISLIKE_OFFER, body, new Service.ServiceResponse() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                        if (apiResponse.isSuccess()) {
                            try {
                                int like = response.getInt("data");
                                if (like == 1) {
                                    feed.like(true);
                                } else if (like == -1) {
                                    feed.like(false);
                                }
//                                RealmDB.getInstance(mContext).createOrUpdate(feed);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                    @Override
                    public void updateUIOnNetworkUnavailable(String noInternetMessage) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private RealmList<Feed> filterOutDatedFeeds(RealmList<Feed> feeds) {

        for (Iterator<Feed> iterator = feeds.iterator(); iterator.hasNext(); ) {
            Feed feed = iterator.next();
            if (feed.getEndDate() < System.currentTimeMillis()) {
                iterator.remove();
            }
        }
        return feeds;
    }

    private void openProductDetails(int position, Feed feed) {
        Intent offersGeneralActivityIntent = new Intent(mContext, OffersGeneralActivity.class);
        offersGeneralActivityIntent.putExtra(Constants.ACTIVITY, Constants.ACTIVITY_PRODUCT_DETAILS);
        offersGeneralActivityIntent.putExtra(Constants.ITEM_POSITION, position);
        CacheManager.getInstance().cacheObject(position + "", feed);
        mContext.startActivity(offersGeneralActivityIntent);
    }

    class OffersViewHolder extends RecyclerView.ViewHolder {

        public ImageButton ibOfferCompanyLogo, ibOfferCategoryImage;
        public TextView tvOfferCompanyName, tvOfferPrice, tvOfferDescription;
        public ImageView ivProductImage;
        public RadioGroup rgLike;
        public RadioButton rbLike, rbDislike;
        public TimerView timerView;
        private View currentView;

        public OffersViewHolder(View itemView) {
            super(itemView);
            currentView = itemView;
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

        public View getCurrentView() {
            return currentView;
        }
    }
}
