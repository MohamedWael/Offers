package com.mowael.offers.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mowael.offers.R;
import com.mowael.offers.adapters.RVOffersAdapter;
import com.mowael.offers.dataModel.APIResponse;
import com.mowael.offers.dataModel.myOffersResponse.Feed;
import com.mowael.offers.dataModel.offers.OffersResponse;
import com.mowael.offers.utilities.ApiError;
import com.mowael.offers.utilities.Constants;
import com.mowael.offers.utilities.Logger;
import com.mowael.offers.utilities.Service;
import com.mowael.offers.utilities.Toaster;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.RealmList;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SearchActivity extends BaseActivity {


    private RecyclerView rvSearchResult;
    private ProgressBar pbSearchProgress;
    private TextView tvNoResult;
    private RVOffersAdapter offersAdapter;
    private ArrayList<Feed> offerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvSearchResult = (RecyclerView) findViewById(R.id.rvSearchResult);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));
        pbSearchProgress = (ProgressBar) findViewById(R.id.pbSearchProgress);
        tvNoResult = (TextView) findViewById(R.id.tvNoResult);


    }

    private void showProgress(boolean show) {
        pbSearchProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showNoResult(boolean show) {
        tvNoResult.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    if (offerList != null) offerList.clear();
                    search(s);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (!s.equals("")) search(s);
                    return true;
                }
            });
        }

        return super.onCreateOptionsMenu(menu);

    }

    private void search(String query) {
        showProgress(true);
        JSONObject body = new JSONObject();
        try {
            body.put(Constants.SEARCH_KEY, query);
            Service.getInstance(this).getResponse(Request.Method.POST, Constants.SEARCH, body, new Service.ServiceResponse() {
                @Override
                public void onResponse(JSONObject response) {
                    showProgress(false);
                    Gson gson = new Gson();
                    APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                    if (apiResponse.isSuccess()) {
                        OffersResponse offersResponse = gson.fromJson(response.toString(), OffersResponse.class);
                        offerList = offersResponse.getData().getOffers();
                        offersAdapter = new RVOffersAdapter(SearchActivity.this, offerList);
                        rvSearchResult.setAdapter(offersAdapter);
                        if (offersResponse.getData().getOffers().size() == 0) showNoResult(true);
                        else showNoResult(false);
                    } else {
                        ApiError apiError = new ApiError(apiResponse.getCode());
                        Logger.d(Constants.API_ERROR, apiError.getErrorMsg());
                        Toaster.getInstance().toast(apiError.getErrorMsg());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgress(false);
                    showNoResult(false);
                }

                @Override
                public void updateUIOnNetworkUnavailable(String noInternetMessage) {
                    showProgress(false);
                    showNoResult(false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
