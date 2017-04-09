package com.sunmediaeg.offers.fragment;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.adapters.RVOffersAdapter;
import com.sunmediaeg.offers.dataModel.APIResponse;
import com.sunmediaeg.offers.dataModel.categoryVendors.Vendor;
import com.sunmediaeg.offers.dataModel.vendor.VendorResponse;
import com.sunmediaeg.offers.utilities.CacheManager;
import com.sunmediaeg.offers.utilities.Constants;
import com.sunmediaeg.offers.utilities.Service;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CompanyProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CompanyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanyProfileFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TextView tvTitle;
    private ImageView ivBrochureImage, ivVendorLogo;
    private ImageButton ibBack;
    private RecyclerView rvCompanyOffers;
    private Vendor vendor;
    private ProgressBar pbVendorProfile;

    public CompanyProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompanyProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompanyProfileFragment newInstance(String param1, String param2) {
        CompanyProfileFragment fragment = new CompanyProfileFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_profile, container, false);
        initComponents(view);
        initVendor();
        return view;
    }

    private void initVendor() {
        try {
            final int vendorID = Integer.parseInt(mParam1);
//            long userID = (long) CacheManager.getInstance().getCachedObject(Constants.USER_ID);
            JSONObject body = new JSONObject();
//            body.put(Constants.USER_ID,userID);
            Service.getInstance(getContext()).getResponse(Request.Method.GET, Constants.VENDOR + vendorID, body, new Service.ServiceResponse() {
                @Override
                public void onResponse(JSONObject response) {
                    Gson gson = new Gson();
                    APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                    if (apiResponse.isSuccess()) {
                        VendorResponse vendorResponse = gson.fromJson(response.toString(), VendorResponse.class);
                        setupVendor(vendorResponse.getData().getVendor());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }

                @Override
                public void updateUIOnNetworkUnavailable(String noInternetMessage) {

                }
            });
        } catch (NumberFormatException e) {
            vendor = (Vendor) CacheManager.getInstance().getCachedObject(Constants.CATEGORY_VENDORS);
            setupVendor(vendor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents(View v) {
        pbVendorProfile = (ProgressBar) v.findViewById(R.id.pbVendorProfile);
        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        ibBack = (ImageButton) v.findViewById(R.id.ibBackCP);
        ivVendorLogo = (ImageView) v.findViewById(R.id.ivVendorLogo);
        ivBrochureImage = (ImageView) v.findViewById(R.id.ivBrochureImage);
        rvCompanyOffers = (RecyclerView) v.findViewById(R.id.rvCompanyOffers);
        rvCompanyOffers.setLayoutManager(new LinearLayoutManager(getContext()));
        ibBack.setOnClickListener(this);
    }

    private void setupVendor(Vendor vendor) {
        tvTitle.setText(vendor.getName());
        Picasso.with(getContext()).load(vendor.getBrochureImage()).placeholder(R.drawable.photo_replacement).into(ivBrochureImage);
        Picasso.with(getContext()).load(vendor.getImage()).placeholder(R.drawable.logo).into(ivVendorLogo);
        RVOffersAdapter offersAdapter = new RVOffersAdapter(getContext(), vendor.getOffers());
        rvCompanyOffers.setAdapter(offersAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibBackCP:
                getActivity().finish();
                break;
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
