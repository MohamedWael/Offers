package com.sunmediaeg.offers.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.adapters.GVCategoriesAdapter;
import com.sunmediaeg.offers.dataModel.Category;
import com.sunmediaeg.offers.utilities.Constants;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
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

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initComponents(view);


        return view;
    }

    private void initComponents(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(mParam1);
        view.findViewById(R.id.ibBack).setVisibility(View.GONE);
        view.findViewById(R.id.ibSearch).setVisibility(View.GONE);
        gvCategories = (GridView) view.findViewById(R.id.gvCategories);
        GVCategoriesAdapter categoriesAdapter = new GVCategoriesAdapter(getContext(), categories(), false);
        gvCategories.setAdapter(categoriesAdapter);
    }


    private ArrayList<Category> categories() {
        ArrayList<Category> categories = new ArrayList<>();
        Category electronics = new Category(Constants.ELECTRONICS, R.drawable.computer, getString(R.string.electronics));
        Category travel = new Category(Constants.TRAVEL, R.drawable.travel, getString(R.string.travel));
        Category airplanes = new Category(Constants.AIRPLANE, R.drawable.plane, getString(R.string.airplanes));
        Category cars = new Category(Constants.CARS, R.drawable.cars, getString(R.string.cars));
        Category furniture = new Category(Constants.FURNUTURE, R.drawable.furniture, getString(R.string.furniture));
        Category restaurant = new Category(Constants.RESTRAUNTS, R.drawable.restaurant, getString(R.string.resteraunts));

        categories.add(electronics);
        categories.add(travel);
        categories.add(airplanes);
        categories.add(cars);
        categories.add(furniture);
        categories.add(restaurant);
        return categories;
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
