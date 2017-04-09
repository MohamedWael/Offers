package com.sunmediaeg.offers.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.adapters.RVOffersAdapter;
import com.sunmediaeg.offers.dataModel.APIResponse;
import com.sunmediaeg.offers.dataModel.myOffersResponse.MyOffersResponse;
import com.sunmediaeg.offers.utilities.ApiError;
import com.sunmediaeg.offers.utilities.CacheManager;
import com.sunmediaeg.offers.utilities.Constants;
import com.sunmediaeg.offers.utilities.Logger;
import com.sunmediaeg.offers.utilities.Service;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OffersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OffersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OffersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView rvCompanies, rvOffers;
    private TextView tvTitle;
    private ProgressBar pbMyOFFers;
    private SwipeRefreshLayout srlRefresh;
    private Service service;

    public OffersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OffersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OffersFragment newInstance(String param1, String param2) {
        OffersFragment fragment = new OffersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            service = Service.getInstance(getContext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        setRetainInstance(true);
        final Long userID = (Long) CacheManager.getInstance().getCachedObject(Constants.USER_ID);
        initComponents(view);
        getMyOffers(Constants.USER_FEEDS + userID);

        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyOffers(Constants.USER_FEEDS + userID);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("executed", "onResume");
    }

    private void initComponents(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(mParam1);
        view.findViewById(R.id.ibBack).setVisibility(View.GONE);
        view.findViewById(R.id.ibSearch).setVisibility(View.GONE);
        rvOffers = (RecyclerView) view.findViewById(R.id.rvOffers);
        pbMyOFFers = (ProgressBar) view.findViewById(R.id.pbMyOFFers);
        srlRefresh = (SwipeRefreshLayout) view.findViewById(R.id.srlRefresh);
        rvOffers.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void getMyOffers(String url) {
        showProgressBar(true);
        JSONObject body = new JSONObject();
        try {
            body.put(Constants.KEY_LIMIT, Constants.LIMIT_VALUE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            service.getResponse(Request.Method.GET, url, body, new Service.ServiceResponse() {
                @Override
                public void onResponse(JSONObject response) {
                    Gson gson = new Gson();
                    showProgressBar(false);
                    APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                    if (apiResponse.isSuccess()) {
                        MyOffersResponse myOffersResponse = gson.fromJson(response.toString(), MyOffersResponse.class);
                        RVOffersAdapter offersAdapter = new RVOffersAdapter(getContext(), myOffersResponse.getData().getFeeds());
                        rvOffers.setAdapter(offersAdapter);

                    } else {
                        if (apiResponse.getCode() == Constants.CODE_NOT_FOUND) {
                            Constants.toastMsg(getContext(), getString(R.string.followingError));
                        } else {
                            ApiError apiError = new ApiError(apiResponse.getCode());
                            Logger.d(Constants.API_ERROR, apiError.getErrorMsg());
                            Constants.toastMsg(getContext(), apiError.getErrorMsg());
                        }
                    }
                    if (srlRefresh.isRefreshing()) srlRefresh.setRefreshing(false);
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

    public void showProgressBar(boolean visible) {
        if (visible) pbMyOFFers.setVisibility(View.VISIBLE);
        else pbMyOFFers.setVisibility(View.GONE);
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
}
