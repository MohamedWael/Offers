package com.mowael.offers.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mowael.offers.R;
import com.mowael.offers.adapters.RVCategoryCompaniesAdapter;
import com.mowael.offers.dataModel.APIResponse;
import com.mowael.offers.dataModel.categoryVendors.CategoryVendorsResponse;
import com.mowael.offers.utilities.ApiError;
import com.mowael.offers.utilities.CacheManager;
import com.mowael.offers.utilities.Constants;
import com.mowael.offers.utilities.Logger;
import com.mowael.offers.utilities.Service;
import com.mowael.offers.utilities.Toaster;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryCompaniesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryCompaniesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryCompaniesFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String CATEGORY_ID_KEY = "param2";

    // TODO: Rename and change types of parameters
    private String categoryTitle;
    private int categoryID;

    private OnFragmentInteractionListener mListener;
    private RecyclerView rvCategoryCompanies;
    private RVCategoryCompaniesAdapter categoryCompaniesAdapter;
    private TextView tvTitle;
    private ImageButton ibBack;
    private ProgressBar pbCategoryVendors;

    public CategoryCompaniesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1     Parameter 1.
     * @param categoryID Parameter 2.
     * @return A new instance of fragment CategoryCompaniesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryCompaniesFragment newInstance(String param1, int categoryID) {
        CategoryCompaniesFragment fragment = new CategoryCompaniesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(CATEGORY_ID_KEY, categoryID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryTitle = getArguments().getString(ARG_PARAM1);
            categoryID = getArguments().getInt(CATEGORY_ID_KEY);
            Logger.d(CATEGORY_ID_KEY, categoryID + "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_companies, container, false);
        initComponents(view);
        try {
            JSONObject body = new JSONObject();
            long userID = (long) CacheManager.getInstance().getCachedObject(Constants.USER_ID, 0L);
            body.put(Constants.KEY_LIMIT, Constants.LIMIT_VALUE);
            Service.getInstance(getContext()).getResponse(Request.Method.GET, Constants.CATEGORY_VENDORS + categoryID + Constants.ADD_USER_ID + userID, body, new Service.ServiceResponse() {
                @Override
                public void onResponse(JSONObject response) {
                    showProgressBar(false);
                    Gson gson = new Gson();
                    APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                    if (apiResponse.isSuccess()) {
                        CategoryVendorsResponse categoryVendorsResponse = gson.fromJson(response.toString(), CategoryVendorsResponse.class);
                        if (categoryVendorsResponse.getData() != null) {
                            categoryCompaniesAdapter = new RVCategoryCompaniesAdapter(getContext(), categoryVendorsResponse.getData().getVendors());
                            rvCategoryCompanies.setAdapter(categoryCompaniesAdapter);
                        }
                    } else {
                        ApiError apiError = new ApiError(apiResponse.getCode());
                        Logger.d(Constants.API_ERROR, apiError.getErrorMsg());
                        Toaster.getInstance().toast(apiError.getErrorMsg());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }

                @Override
                public void updateUIOnNetworkUnavailable(String noInternetMessage) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initComponents(View v) {
        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        tvTitle.setText(categoryTitle);
        ibBack = (ImageButton) v.findViewById(R.id.ibBack);
        pbCategoryVendors = (ProgressBar) v.findViewById(R.id.pbCategoryVendors);
        showProgressBar(true);
        ibBack.setOnClickListener(this);
        rvCategoryCompanies = (RecyclerView) v.findViewById(R.id.rvCategoryCompanies);
        rvCategoryCompanies.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibBack:
                getActivity().finish();
                break;
        }
    }

    private void showProgressBar(boolean show) {
        if (show) pbCategoryVendors.setVisibility(View.VISIBLE);
        else pbCategoryVendors.setVisibility(View.GONE);
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
