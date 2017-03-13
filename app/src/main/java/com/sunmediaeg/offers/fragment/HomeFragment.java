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
import android.widget.TextView;

import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.adapters.RVCompaniesAdapter;
import com.sunmediaeg.offers.adapters.RVOffersAdapter;
import com.sunmediaeg.offers.dataModel.Company;
import com.sunmediaeg.offers.dataModel.myOffersResponse.Feed;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView rvCompanies, rvOffers;
    private TextView tvTitle;
    private static HomeFragment fragment;

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
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        RVCompaniesAdapter companiesAdapter = new RVCompaniesAdapter(getContext(), initCompaniesData());
        rvCompanies.setAdapter(companiesAdapter);
        RVOffersAdapter offersAdapter = new RVOffersAdapter(getContext(), new ArrayList<Feed>());
        rvOffers.setAdapter(offersAdapter);
    }

    private void initComponents(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(mParam1);
        view.findViewById(R.id.ibBack).setVisibility(View.GONE);
        rvOffers = (RecyclerView) view.findViewById(R.id.rvHomeOffers);
        rvOffers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCompanies = (RecyclerView) view.findViewById(R.id.rvCompanies);
        rvCompanies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
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

    private ArrayList<Company> initCompaniesData() {
        ArrayList<Company> companies = new ArrayList<>();
        companies.add(new Company(0, getString(R.string.egyptAir), R.drawable.egypt_air));
        companies.add(new Company(2, getString(R.string.arrabMall), R.drawable.mall_of_arabia));
        companies.add(new Company(1, getString(R.string.carrefour), R.drawable.cafarrefour));
        companies.add(new Company(3, getString(R.string.cityCenter), R.drawable.city_centre));
        return companies;
    }
}
