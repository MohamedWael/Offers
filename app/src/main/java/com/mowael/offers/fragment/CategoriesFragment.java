package com.mowael.offers.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mowael.offers.R;
import com.mowael.offers.adapters.GVCategoriesAdapter;
import com.mowael.offers.dataModel.APIResponse;
import com.mowael.offers.dataModel.categories.CategoriesResponse;
import com.mowael.offers.utilities.ApiError;
import com.mowael.offers.utilities.Constants;
import com.mowael.offers.utilities.Logger;
import com.mowael.offers.utilities.Service;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoriesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TextView tvTitle;
    private GridView gvCategories;
    private ProgressBar pbCategories;
    private SwipeRefreshLayout srlRefresh;
    private static CategoriesFragment fragment;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(String param1, String param2) {
        if (fragment == null) {
            fragment = new CategoriesFragment();
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
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        initComponents(view);

        return view;
    }

    private void initComponents(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        pbCategories = (ProgressBar) view.findViewById(R.id.pbCategories);
        srlRefresh = (SwipeRefreshLayout) view.findViewById(R.id.srlRefresh);

        tvTitle.setText(mParam1);
        view.findViewById(R.id.ibBack).setVisibility(View.GONE);
        view.findViewById(R.id.ibSearch).setVisibility(View.GONE);
        gvCategories = (GridView) view.findViewById(R.id.gvCategories);

        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllCategories();
            }
        });
        getAllCategories();
    }

    private void getAllCategories() {
        try {
            showProgressBar(true);
            Service.getInstance(getContext()).getResponse(Request.Method.GET, Constants.GET_ALL_CATEGORIES, new JSONObject(), new Service.ServiceResponse() {
                @Override
                public void onResponse(JSONObject response) {
                    showProgressBar(false);
                    Gson gson = new Gson();
                    APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                    if (apiResponse.isSuccess()) {
                        CategoriesResponse categoriesResponse = gson.fromJson(response.toString(), CategoriesResponse.class);

                        final GVCategoriesAdapter categoriesAdapter = new GVCategoriesAdapter(getContext(), categoriesResponse.getData().getCategories());
                        gvCategories.setAdapter(categoriesAdapter);

                    } else {
                        ApiError apiError = new ApiError(apiResponse.getCode());
                        Logger.d(Constants.API_ERROR, apiError.getErrorMsg());
                        Constants.toastMsg(getContext(), apiError.getErrorMsg());
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

    private void showProgressBar(boolean show) {
        if (show) pbCategories.setVisibility(View.VISIBLE);
        else pbCategories.setVisibility(View.GONE);
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
