package com.sunmediaeg.offers.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.dataModel.Company;

import java.util.ArrayList;

/**
 * Created by moham on 1/29/2017.
 */

public class RVCategoryCompaniesAdapter extends RecyclerView.Adapter<RVCategoryCompaniesAdapter.CategoryCompanyViewHolder> {

    private Context mContext;
    private ArrayList<Company> companies;

    public RVCategoryCompaniesAdapter(Context mContext, ArrayList<Company> companies) {
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
        final Company company = companies.get(position);
        holder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!company.isFollwed()) {
                    holder.btnFollow.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                    holder.btnFollow.setTextColor(ContextCompat.getColor(mContext,R.color.colorBackground));
                    company.setFollwed(true);
                } else {
                    company.setFollwed(false);
                    holder.btnFollow.setBackgroundResource(R.drawable.button_border);
                    holder.btnFollow.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimaryDark));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return companies.size();
//        return 20;
    }

    class CategoryCompanyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCompanyLogo;
        TextView tvCompanyName, tvNumberOfOffers;
        Button btnFollow;

        public CategoryCompanyViewHolder(View itemView) {
            super(itemView);
            ivCompanyLogo = (ImageView) itemView.findViewById(R.id.ivCompanyLogo);
            tvCompanyName = (TextView) itemView.findViewById(R.id.tvCompanyName);
            tvNumberOfOffers = (TextView) itemView.findViewById(R.id.btnNumberOfOffers);
            btnFollow = (Button) itemView.findViewById(R.id.btnFollow);
        }
    }
}
