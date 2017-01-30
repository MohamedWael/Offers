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
import android.widget.TextView;

import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.adapters.RVCategoryCompaniesAdapter;
import com.sunmediaeg.offers.dataModel.Company;

import java.util.ArrayList;

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
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView rvCategoryCompanies;
    private RVCategoryCompaniesAdapter categoryCompaniesAdapter;
    private TextView tvTitle;
    private ImageButton ibBack;

    public CategoryCompaniesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryCompaniesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryCompaniesFragment newInstance(String param1, String param2) {
        CategoryCompaniesFragment fragment = new CategoryCompaniesFragment();
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
        View view = inflater.inflate(R.layout.fragment_category_companies, container, false);
        initComponents(view);

        return view;
    }

    private void initComponents(View v) {
        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        tvTitle.setText(mParam1);
        ibBack = (ImageButton) v.findViewById(R.id.ibBack);
        ibBack.setOnClickListener(this);
        rvCategoryCompanies = (RecyclerView) v.findViewById(R.id.rvCategoryCompanies);
        rvCategoryCompanies.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Company> companies = new ArrayList<>();
        Company company = new Company(0, R.drawable.egypt_air, 14, getString(R.string.egyptAir), "", false);
        Company company1 = new Company(0, R.drawable.egypt_air, 14, getString(R.string.egyptAir), "", false);
        Company company2 = new Company(0, R.drawable.egypt_air, 14, getString(R.string.egyptAir), "", false);
        Company company3 = new Company(0, R.drawable.egypt_air, 14, getString(R.string.egyptAir), "", false);
        companies.add(company);
        companies.add(company1);
        companies.add(company2);
        companies.add(company3);
        categoryCompaniesAdapter = new RVCategoryCompaniesAdapter(getContext(), companies);
        rvCategoryCompanies.setAdapter(categoryCompaniesAdapter);
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
