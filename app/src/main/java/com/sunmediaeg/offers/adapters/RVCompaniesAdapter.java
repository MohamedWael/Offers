package com.sunmediaeg.offers.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.dataModel.Company;

import java.util.ArrayList;

/**
 * Created by moham on 1/26/2017.
 */

public class RVCompaniesAdapter extends RecyclerView.Adapter<RVCompaniesAdapter.CompaniesViewHolder> {

    private Context mContext;
    private ArrayList<Company> companies;

    public RVCompaniesAdapter(Context mContext, ArrayList<Company> companies) {
        this.mContext = mContext;
        this.companies = companies;
    }

    @Override
    public CompaniesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.companies_item_layout, parent, false);
        return new CompaniesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CompaniesViewHolder holder, int position) {
        Company company = companies.get(position);
        holder.ivCompanyLogo.setImageDrawable(ContextCompat.getDrawable(mContext, company.getImageSourse()));
        holder.tvCompanyName.setText(company.getCompanyName());
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    class CompaniesViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCompanyLogo;
        TextView tvCompanyName;

        public CompaniesViewHolder(View itemView) {
            super(itemView);
            ivCompanyLogo = (ImageView) itemView.findViewById(R.id.ivCompanyLogo);
            tvCompanyName = (TextView) itemView.findViewById(R.id.tvCompanyName);
        }
    }
}
