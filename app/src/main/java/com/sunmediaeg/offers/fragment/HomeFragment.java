package com.sunmediaeg.offers.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.activities.SearchActivity;
import com.sunmediaeg.offers.adapters.RVCompaniesAdapter;
import com.sunmediaeg.offers.adapters.RVOffersAdapter;
import com.sunmediaeg.offers.dataModel.Company;
import com.sunmediaeg.offers.dataModel.offers.OffersResponse;
import com.sunmediaeg.offers.utilities.ApiError;
import com.sunmediaeg.offers.utilities.CacheManager;
import com.sunmediaeg.offers.utilities.Constants;
import com.sunmediaeg.offers.utilities.Logger;
import com.sunmediaeg.offers.utilities.Service;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final int TYPE_OFFERS = 1;
    public static final int TYPE_CITIES = 2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int offerType;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView rvCompanies, rvOffers;
    private TextView tvTitle;
    private SwipeRefreshLayout srlRefresh;
    private ProgressBar pbHomeOffers;
    private static HomeFragment fragment;
    private Toolbar mToolbar;
    private ImageButton ibSearch;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance(String param1, String param2) {
        if (fragment == null) {
            fragment = new HomeFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setRetainInstance(true);
        initComponents(view);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        final long userID = (long) CacheManager.getInstance().getCachedObject(Constants.USER_ID, 0L);
//        if (userID != null && userID != 0)
//            getAllOffers(Constants.SHOW_ALL_OFFERS + userID);
//        else getAllOffers(Constants.removeLastSlash(Constants.SHOW_ALL_OFFERS));
        getAllOffers(Constants.removeLastSlash(Constants.SHOW_ALL_OFFERS) + Constants.ADD_USER_ID + userID);

        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllOffers(Constants.removeLastSlash(Constants.SHOW_ALL_OFFERS) + Constants.ADD_USER_ID + userID);

            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        setRetainInstance(true);
        RVCompaniesAdapter companiesAdapter = new RVCompaniesAdapter(getContext(), initCompaniesData());
        rvCompanies.setAdapter(companiesAdapter);
    }

    private void initComponents(View v) {
        pbHomeOffers = (ProgressBar) v.findViewById(R.id.pbHomeOffers);
        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        tvTitle.setText(mParam1);
        v.findViewById(R.id.ibBack).setVisibility(View.GONE);
        srlRefresh = (SwipeRefreshLayout) v.findViewById(R.id.srlRefresh);
        rvOffers = (RecyclerView) v.findViewById(R.id.rvHomeOffers);
        rvOffers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCompanies = (RecyclerView) v.findViewById(R.id.rvCompanies);
        mToolbar = (Toolbar) v.findViewById(R.id.mToolbar);
        ibSearch = (ImageButton) v.findViewById(R.id.ibSearch);
        rvCompanies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
    }

    private void getAllOffers(String url) {
        try {
            pbHomeOffers.setVisibility(View.VISIBLE);
            JSONObject body = new JSONObject();
            Service.getInstance(getContext()).getResponse(Request.Method.GET, url, body, new Service.ServiceResponse() {
                @Override
                public void onResponse(JSONObject response) {
                    Gson gson = new Gson();
                    OffersResponse offersResponse = gson.fromJson(response.toString(), OffersResponse.class);
                    if (offersResponse != null && offersResponse.isSuccess()) {
                        RVOffersAdapter offersAdapter = new RVOffersAdapter(getContext(), offersResponse.getData().getOffers());
                        rvOffers.setAdapter(offersAdapter);
                        if (srlRefresh.isRefreshing()) srlRefresh.setRefreshing(false);
                    } else {
                        ApiError apiError = new ApiError(offersResponse.getCode());
                        Logger.d(Constants.API_ERROR, apiError.getErrorMsg());
                        Constants.toastMsg(getContext(), apiError.getErrorMsg());
                    }
                    pbHomeOffers.setVisibility(View.GONE);
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (srlRefresh.isRefreshing()) srlRefresh.setRefreshing(false);
                }

                @Override
                public void updateUIOnNetworkUnavailable(String noInternetMessage) {
                    if (srlRefresh.isRefreshing()) srlRefresh.setRefreshing(false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void setOfferType(int offerType) {
        this.offerType = offerType;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private ArrayList<Company> initCompaniesData() {
        ArrayList<Company> companies = new ArrayList<>();
        companies.add(new Company(0, getString(R.string.egyptAir), R.drawable.egypt_air));
        companies.add(new Company(2, getString(R.string.arrabMall), R.drawable.mall_of_arabia));
        companies.add(new Company(1, getString(R.string.carrefour), R.drawable.cafarrefour));
        companies.add(new Company(3, getString(R.string.cityCenter), R.drawable.city_centre));
        return companies;
    }
}
