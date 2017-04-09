package com.sunmediaeg.offers.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.dataModel.APIResponse;
import com.sunmediaeg.offers.dataModel.userResponse.User;
import com.sunmediaeg.offers.dataModel.userResponse.UserResponse;
import com.sunmediaeg.offers.utilities.ApiError;
import com.sunmediaeg.offers.utilities.CacheManager;
import com.sunmediaeg.offers.utilities.Constants;
import com.sunmediaeg.offers.utilities.ImageConverter;
import com.sunmediaeg.offers.utilities.Logger;
import com.sunmediaeg.offers.utilities.Service;
import com.sunmediaeg.offers.utilities.VolleySingleton;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountSettingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountSettingFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText etUserName, etPassword, etCity, etEmail;
    private Button btnModifyAccountData;
    private ImageView ivAccountPhoto;
    private VolleySingleton volley;
    private JSONObject body;
    private ProgressBar pbAccountSetting;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private User user;
    private TextView tvSuccess;


    public AccountSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountSettingFragment newInstance(String param1, String param2) {
        AccountSettingFragment fragment = new AccountSettingFragment();
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
        View view = inflater.inflate(R.layout.fragment_account_setting, container, false);
        initView(view);
        CacheManager manager = CacheManager.getInstance();
//        user = (User) manager.getCachedObject(Constants.USER);
//        if (user == null) {
//            cacheUserData();
//        }
        return view;
    }

    private void initView(View v) {
        tvSuccess = (TextView) v.findViewById(R.id.tvSuccess);
        ivAccountPhoto = (ImageView) v.findViewById(R.id.ivAccountPhoto);
        pbAccountSetting = (ProgressBar) v.findViewById(R.id.pbAccountSetting);
        etUserName = (EditText) v.findViewById(R.id.etUserName);
        etPassword = (EditText) v.findViewById(R.id.etPassword);
        etCity = (EditText) v.findViewById(R.id.etCity);
        etEmail = (EditText) v.findViewById(R.id.etEmail);
        btnModifyAccountData = (Button) v.findViewById(R.id.btnModifyAccountData);

        cacheUserData();

        volley = VolleySingleton.getInstance(getContext());
        body = new JSONObject();
        ivAccountPhoto.setOnClickListener(this);
        btnModifyAccountData.setOnClickListener(this);
    }

    private void setupUser(User user) {
        if (user != null) {
            etUserName.setText(user.getName());
            etEmail.setText(user.getEmail());
            etCity.setText(user.getCity());
            if (!user.getImage().isEmpty())
                Picasso.with(getContext()).load(volley.uriEncoder(user.getImage())).error(R.drawable.profile_avatar_placeholder_large)
                        .placeholder(R.drawable.profile_avatar_placeholder_large).into(ivAccountPhoto);
        }
    }

    private String getStieng(EditText editText) {
        return editText.getText().toString();
    }

    private void showProgressBar(boolean show) {
        pbAccountSetting.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.e("requestCode", requestCode + "");
        Logger.e("resultCode", resultCode + "");
        if (resultCode == Activity.RESULT_OK && requestCode == VolleySingleton.GALLERY_INTENT_REQUEST) {
            ivAccountPhoto.setImageURI(data.getData());
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
            case R.id.ivAccountPhoto:
                VolleySingleton.getInstance(getContext()).getPictureFromGalleryIntent(this);
                break;
            case R.id.btnModifyAccountData:
                showProgressBar(true);
                tvSuccess.setVisibility(View.INVISIBLE);
                ImageConverter converter = new ImageConverter();
                converter.getStringImage(volley, ivAccountPhoto, new ImageConverter.ConversionListener() {
                    @Override
                    public void OnConversionComplete(String image) {
                        try {
                            String userName = getStieng(etUserName);
                            String password = getStieng(etPassword);
                            String city = getStieng(etCity);
                            String email = getStieng(etEmail);
                            long userID = (long) CacheManager.getInstance().getCachedObject(Constants.USER_ID);
                            String token = (String) CacheManager.getInstance().getCachedObject(Constants.TOKEN);

                            body.put(Constants.ID, userID);
                            body.put(Constants.TOKEN, token);
                            body.put(Constants._METHOD, Constants._METHOD_PATCH);
                            body.put(Constants.NAME, userName);
                            body.put(Constants.EMAIL, email);
                            body.put(Constants.PASSWORD, password);
                            body.put(Constants.CITY, city);
//                    String image = volley.getReducedStringImagePNG(volley.getBitmap(ivAccountPhoto));
                            body.put(Constants.IMAGE, image);
                            Logger.d("Image", image + "Image");

                            Service.getInstance(getContext()).getResponse(Request.Method.POST, Constants.USER_UPDATE, body, new Service.ServiceResponse() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Gson gson = new Gson();
                                    APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                                    if (apiResponse.isSuccess()) {
                                        tvSuccess.setVisibility(View.VISIBLE);
                                        UserResponse userResponse = gson.fromJson(response.toString(), UserResponse.class);
                                        CacheManager.getInstance().cacheObject(Constants.USER, userResponse.getData().getUser());
                                    } else {
                                        tvSuccess.setVisibility(View.INVISIBLE);
                                        ApiError apiError = new ApiError(apiResponse.getCode());
                                        Logger.d(Constants.API_ERROR, apiError.getErrorMsg());
                                    }
                                    showProgressBar(false);
                                }

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    showProgressBar(false);
                                    tvSuccess.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void updateUIOnNetworkUnavailable(String noInternetMessage) {
                                    showProgressBar(false);
                                    tvSuccess.setVisibility(View.INVISIBLE);
                                }
                            });

                        } catch (Exception e) {
                            showProgressBar(false);
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }

    private void cacheUserData() {
        showProgressBar(true);
        long userID = (long) CacheManager.getInstance().getCachedObject(Constants.USER_ID, 0L);
        Logger.d("userID", userID + "");
        if (userID != 0) {
            try {
                Service.getInstance(getContext()).getResponse(Request.Method.GET, Constants.USER + userID, new JSONObject(), new Service.ServiceResponse() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.d("UserCache", response.toString());
                        Gson gson = new Gson();
                        APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                        if (apiResponse.isSuccess()) {
                            UserResponse userResponse = gson.fromJson(response.toString(), UserResponse.class);
                            CacheManager.getInstance().cacheObject(Constants.USER, userResponse.getData().getUser());
                            setupUser(userResponse.getData().getUser());
                        } else {
                            ApiError apiError = new ApiError(apiResponse.getCode());
                            Logger.d(Constants.API_ERROR, apiError.getErrorMsg());
                        }
                        showProgressBar(false);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgressBar(false);
                    }

                    @Override
                    public void updateUIOnNetworkUnavailable(String noInternetMessage) {
                        showProgressBar(false);
                    }
                });
            } catch (Exception e) {
                showProgressBar(false);
                e.printStackTrace();
            }
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
