package com.sunmediaeg.offers.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.dataModel.APIResponse;
import com.sunmediaeg.offers.dataModel.myOffersResponse.Feed;
import com.sunmediaeg.offers.utilities.CacheManager;
import com.sunmediaeg.offers.utilities.Constants;
import com.sunmediaeg.offers.utilities.Logger;
import com.sunmediaeg.offers.utilities.Service;
import com.sunmediaeg.offers.utilities.VolleySingleton;
import com.sunmediaeg.offers.views.TimerView;
import com.sunmediaeg.offers.views.TimerViewCounter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "param1";
    private static final String ITEM_POSITION = "param2";

    // TODO: Rename and change types of parameters
    private String fragmentTitle;
    private int itemPosition;
    private TextView tvTitle, tvOfferCompanyName, tvOfferPrice, tvOfferDescription, tvDetails;
    private ImageView ivProductImage;
    private ImageButton ibOfferCompanyLogo, ibOfferCategoryImage;
    private RadioGroup rgLikeDetails;
    private RadioButton rbLike, rbDislike;
    private OnFragmentInteractionListener mListener;
    private ImageButton ibBack;
    private Feed feed;
    private TimerView timerView;
    private Button btnShare;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title        Parameter 1.
     * @param itemPosition Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String title, int itemPosition) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putInt(ITEM_POSITION, itemPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentTitle = getArguments().getString(TITLE);
            itemPosition = getArguments().getInt(ITEM_POSITION);
        }
        feed = (Feed) CacheManager.getInstance().getCachedObject(itemPosition + "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        initComponents(view);

        return view;
    }

    private void initComponents(View v) {
        Constants.hideSearchButton(v);
        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        tvTitle.setText(fragmentTitle);
        tvOfferCompanyName = (TextView) v.findViewById(R.id.tvOfferCompanyName);
        tvDetails = (TextView) v.findViewById(R.id.tvDetails);
        tvOfferPrice = (TextView) v.findViewById(R.id.tvOfferPrice);
        tvOfferDescription = (TextView) v.findViewById(R.id.tvOfferDescription);
        ibBack = (ImageButton) v.findViewById(R.id.ibBack);
        ivProductImage = (ImageView) v.findViewById(R.id.ivProductLargeImage);
        ibOfferCompanyLogo = (ImageButton) v.findViewById(R.id.ibOfferCompanyLogo);
        ibOfferCategoryImage = (ImageButton) v.findViewById(R.id.ibOfferCategoryImage);
        btnShare = (Button) v.findViewById(R.id.btnShare);

        rgLikeDetails = (RadioGroup) v.findViewById(R.id.rgLikeDetails);
        rbLike = (RadioButton) v.findViewById(R.id.rbLike);
        rbDislike = (RadioButton) v.findViewById(R.id.rbDislike);
        timerView = (TimerView) v.findViewById(R.id.timerView);

//        tvOfferCompanyName.setText(feed.getVendorName);
        tvOfferPrice.setText(feed.getPrice());
        tvOfferDescription.setText(feed.getShortDescription());
        tvDetails.setText(feed.getDescription());

        Picasso.with(getContext()).load(VolleySingleton.getInstance(getContext()).uriEncoder(feed.getCategoryImage())).placeholder(R.drawable.logo).into(ibOfferCategoryImage);
        Picasso.with(getContext()).load(VolleySingleton.getInstance(getContext()).uriEncoder(feed.getVendorImage())).placeholder(R.drawable.logo).into(ibOfferCompanyLogo);
        Picasso.with(getContext()).load(VolleySingleton.getInstance(getContext()).uriEncoder(feed.getImage())).placeholder(R.drawable.photo_replacement).into(ivProductImage);

        ibBack.setOnClickListener(this);

        if (feed.isLiked()) {
            rbLike.setChecked(true);
            rbDislike.setChecked(false);
        } else if (feed.isFeedDisLiked()) {
            rbDislike.setChecked(true);
            rbLike.setChecked(false);
        } else {
            rbLike.setChecked(false);
            rbDislike.setChecked(false);
        }

        rgLikeDetails.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbLike:
                        like(feed, true);
                        rbLike.setChecked(true);
                        rbDislike.setChecked(false);
                        break;
                    case R.id.rbDislike:
                        like(feed, false);
                        rbDislike.setChecked(true);
                        rbLike.setChecked(false);
                        break;
                }
            }
        });

        timerView.setTime(feed.getStartDate(), feed.getEndDate(), new TimerViewCounter.RemainingTime() {
            @Override
            public void onTimeOut() {

            }

            @Override
            public void totalPeriod(long totalPeriod) {

            }

            @Override
            public void getTime(long remainingTime) {

            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Intent.EXTRA_TEXT, feed.getShortDescription() + "\n" + getString(R.string.onlyOnOffers));
                Constants.openShareDialog(getContext(), Constants.PLAIN_TEXT, getString(R.string.btnShare), bundle);
            }
        });
    }

    private void like(final Feed feed, boolean like) {
        try {
            long userID = (long) CacheManager.getInstance().getCachedObject(Constants.USER_ID);
            Logger.d(Constants.USER_ID, userID + "");
            String token = (String) CacheManager.getInstance().getCachedObject(Constants.TOKEN);
            Logger.d(Constants.TOKEN, token + "");
            if (!token.isEmpty() && userID != 0) {
                JSONObject body = new JSONObject();
                body.put(Constants.USER_ID, userID);
                body.put(Constants.TOKEN, token);
                body.put(Constants.OFFER_ID, feed.getId());
                Service.getInstance(getContext()).getResponse(Request.Method.POST, like ? Constants.LIKE_OFFER : Constants.DISLIKE_OFFER, body, new Service.ServiceResponse() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                        if (apiResponse.isSuccess()) {
                            try {
                                int like = response.getInt("data");
                                if (like == 1) {
                                    feed.like(true);
                                } else if (like == -1) {
                                    feed.like(false);
                                }
//                                RealmDB.getInstance(getContext()).createOrUpdate(feed);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                    @Override
                    public void updateUIOnNetworkUnavailable(String noInternetMessage) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
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
