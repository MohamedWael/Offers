package com.mowael.offers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.mowael.offers.R;
import com.mowael.offers.activities.MainActivity;
import com.mowael.offers.dataModel.APIResponse;
import com.mowael.offers.dataModel.categoryVendors.Vendor;
import com.mowael.offers.utilities.CacheManager;
import com.mowael.offers.utilities.Constants;
import com.mowael.offers.utilities.Service;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by moham on 1/29/2017.
 */

public class RVCategoryCompaniesAdapter extends RecyclerView.Adapter<RVCategoryCompaniesAdapter.CategoryCompanyViewHolder> {

    private Context mContext;
    private ArrayList<Vendor> companies;

    public RVCategoryCompaniesAdapter(Context mContext, ArrayList<Vendor> companies) {
        this.mContext = mContext;
        this.companies = companies;
    }

    @Override
    public CategoryCompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_companies_layout, parent, false);
        return new CategoryCompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryCompanyViewHolder holder, int position) {
        final Vendor vendor = companies.get(position);
        holder.tvCompanyName.setText(vendor.getName());
        if (vendor.getOffers().size() == 0) {
            holder.tvNumberOfOffers.setText(mContext.getString(R.string.noOffer));
        } else if (vendor.getOffers().size() == 1) {
            holder.tvNumberOfOffers.setText(mContext.getString(R.string.oneOffer));
        } else if (vendor.getOffers().size() == 2) {
            holder.tvNumberOfOffers.setText(mContext.getString(R.string.twoOffer));
        } else if (vendor.getOffers().size() > 2 && vendor.getOffers().size() <= 10) {
            holder.tvNumberOfOffers.setText(" " + vendor.getOffers().size() + " " + mContext.getString(R.string.lessThan10Offers));
        } else {
            holder.tvNumberOfOffers.setText(" " + vendor.getOffers().size() + " " + mContext.getString(R.string.moreThan10Offers));
        }
        Picasso.with(mContext).load(vendor.getImage()).placeholder(R.drawable.logo).into(holder.ivCompanyLogo);

        holder.cvCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra(Constants.IS_COMPANY_PROFILE, true);
                intent.putExtra(Constants.COMPANY_PROFILE_TITLE, vendor.getName());
                CacheManager.getInstance().cacheObject(Constants.CATEGORY_VENDORS, vendor);
                mContext.startActivity(intent);
            }
        });

        if (vendor.isFollowed()){
            holder.btnFollow.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
            holder.btnFollow.setTextColor(ContextCompat.getColor(mContext, R.color.colorBackground));
        }else {
            holder.btnFollow.setBackgroundResource(R.drawable.button_border);
            holder.btnFollow.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        }

        holder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!vendor.isFollowed()) {
                    holder.btnFollow.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                    holder.btnFollow.setTextColor(ContextCompat.getColor(mContext, R.color.colorBackground));
                    follow(vendor);
                } else {
                    follow(vendor);
                    holder.btnFollow.setBackgroundResource(R.drawable.button_border);
                    holder.btnFollow.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                }
            }
        });
    }

    private void follow(final Vendor vendor) {
        try {
            long userID = (long) CacheManager.getInstance().getCachedObject(Constants.USER_ID);
            String token = (String) CacheManager.getInstance().getCachedObject(Constants.TOKEN);
            if (!token.isEmpty() && userID != 0) {
                JSONObject body = new JSONObject();
                body.put(Constants.USER_ID, userID);
                body.put(Constants.TOKEN, token);
                body.put(Constants.VENDOR_ID, vendor.getId());
                Service.getInstance(mContext).getResponse(Request.Method.POST, Constants.FOLLOW_VENDOR, body, new Service.ServiceResponse() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                        if (apiResponse.isSuccess()) {
                            try {
                                int follow = response.getInt("data");
                                if (follow >= 1) {
                                    vendor.follow(true);
                                } else if (follow <= 0) {
                                    vendor.follow(false);
                                }
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

    @Override
    public int getItemCount() {
        return companies.size();
//        return 20;
    }

    class CategoryCompanyViewHolder extends RecyclerView.ViewHolder {
        CardView cvCompany;
        ImageView ivCompanyLogo;
        TextView tvCompanyName, tvNumberOfOffers;
        Button btnFollow;

        public CategoryCompanyViewHolder(View itemView) {
            super(itemView);
            cvCompany = (CardView) itemView.findViewById(R.id.cvCompany);
            ivCompanyLogo = (ImageView) itemView.findViewById(R.id.ivCompanyLogo);
            tvCompanyName = (TextView) itemView.findViewById(R.id.tvCompanyName);
            tvNumberOfOffers = (TextView) itemView.findViewById(R.id.btnNumberOfOffers);
            btnFollow = (Button) itemView.findViewById(R.id.btnFollow);
        }
    }
}
