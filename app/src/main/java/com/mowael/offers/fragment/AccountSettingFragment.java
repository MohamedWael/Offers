package com.mowael.offers.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mowael.offers.R;
import com.mowael.offers.dataModel.APIResponse;
import com.mowael.offers.dataModel.cities.CitiesResponse;
import com.mowael.offers.dataModel.cities.City;
import com.mowael.offers.dataModel.userResponse.User;
import com.mowael.offers.dataModel.userResponse.UserResponse;
import com.mowael.offers.utilities.ApiError;
import com.mowael.offers.utilities.CacheManager;
import com.mowael.offers.utilities.Constants;
import com.mowael.offers.utilities.ImageConverter;
import com.mowael.offers.utilities.Logger;
import com.mowael.offers.utilities.Service;
import com.mowael.offers.utilities.Toaster;
import com.mowael.offers.utilities.UserUtil;
import com.mowael.offers.utilities.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;


public class AccountSettingFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText etUserName, etPassword, etEmail;
    private Button btnModifyAccountData;
    private ImageView ivAccountPhoto;
    private VolleySingleton volley;
    private JSONObject body;
    private ProgressBar pbAccountSetting;
    private Spinner sCities;

    private String mParam1;
    private String mParam2;


    private User user;
    private TextView tvSuccess;
    private ArrayList<String> cities;
    private ArrayAdapter<String> spinnerAdapter;
    private int cityId;
    private Service service;


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
        cacheUserData();
        return view;
    }

    private void initView(final View v) {
        tvSuccess = (TextView) v.findViewById(R.id.tvSuccess);
        ivAccountPhoto = (ImageView) v.findViewById(R.id.ivAccountPhoto);
        pbAccountSetting = (ProgressBar) v.findViewById(R.id.pbAccountSetting);
        etUserName = (EditText) v.findViewById(R.id.etUserName);
        etPassword = (EditText) v.findViewById(R.id.etPassword);
//        etCity = (EditText) v.findViewById(R.id.etCity);
        etEmail = (EditText) v.findViewById(R.id.etEmail);
        btnModifyAccountData = (Button) v.findViewById(R.id.btnModifyAccountData);
        sCities = (Spinner) v.findViewById(R.id.sCities);
        cities = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, cities);

        sCities.setAdapter(spinnerAdapter);

        volley = VolleySingleton.getInstance(getContext());
        body = new JSONObject();
        ivAccountPhoto.setOnClickListener(this);
        btnModifyAccountData.setOnClickListener(this);

        service = Service.getInstance(getContext());

    }

    private void setupUser(User user) {
        if (user != null) {
            etUserName.setText(user.getName());
            etEmail.setText(user.getEmail());
            cities.add(user.getCity());
            spinnerAdapter.notifyDataSetChanged();
            getCities();
            if (!user.getImage().isEmpty())
                Picasso.with(getContext()).load(volley.uriEncoder(user.getImage())).error(R.drawable.profile_avatar_placeholder_large)
                        .placeholder(R.drawable.profile_avatar_placeholder_large).into(ivAccountPhoto);
            else
                Picasso.with(getContext()).load(R.drawable.profile_avatar_placeholder_large).into(ivAccountPhoto);
        }
    }

    private void setupUser() {
        if (user != null) {
            etUserName.setText(UserUtil.getInstance().getUserName());
            etEmail.setText(UserUtil.getInstance().getEmail());
            cities.add(user.getCity());
            spinnerAdapter.notifyDataSetChanged();
            getCities();
            if (!user.getImage().isEmpty())
                Picasso.with(getContext()).load(volley.uriEncoder(user.getImage())).error(R.drawable.profile_avatar_placeholder_large)
                        .placeholder(R.drawable.profile_avatar_placeholder_large).into(ivAccountPhoto);
            else
                Picasso.with(getContext()).load(R.drawable.profile_avatar_placeholder_large).into(ivAccountPhoto);
        }
    }

    private void getCities() {
        try {
            Service.getInstance(getContext()).getResponse(Request.Method.GET, Constants.GET_CITIES, new JSONObject(), new Service.ServiceResponse() {
                @Override
                public void onResponse(JSONObject response) {
                    Gson gson = new Gson();
                    APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                    if (apiResponse.isSuccess()) {
                        final CitiesResponse citiesResponse = gson.fromJson(response.toString(), CitiesResponse.class);

                        sCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String currentCity = parent.getItemAtPosition(position).toString();
                                for (City city : citiesResponse.getData().getCities()) {
                                    if (currentCity.equals(city.getName())) {
                                        cityId = city.getId();
                                    }
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        cities.addAll(citiesResponse.getData().getCitiesNames());
                        spinnerAdapter.notifyDataSetChanged();

                        if (citiesResponse.getData().getCitiesNames().contains(cities.get(0))) {
                            int cityIndex = citiesResponse.getData().getCitiesNames().indexOf(cities.get(0));
                            cityId = citiesResponse.getData().getCity(cityIndex).getId();
                            citiesResponse.getData().getCitiesNames().remove(cities.get(0));
                        }

                        setDefaultCity(citiesResponse.getData().getCities(), citiesResponse.getData().getCities().get(0).getName());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toaster.getInstance().toast("فشل فى تحميل المدن حاول فى وقت لاحق");
                }

                @Override
                public void updateUIOnNetworkUnavailable(String noInternetMessage) {
                    Toaster.getInstance().toast(noInternetMessage);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setDefaultCity(ArrayList<City> cities, String defaultCity) {
        for (City city : cities) {
            if (defaultCity.equals(city.getName())) {
                cityId = city.getId();
            }
        }
    }

    private String getStieng(EditText editText) {
        return editText.getText().toString();
    }

    private void showProgressBar(final boolean show) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pbAccountSetting.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

            }
        });
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
//                            String city = getStieng(etCity);
                            String email = getStieng(etEmail);

                            long userID = UserUtil.getInstance().getId();//CacheManager.getInstance().getCachedObject(Constants.USER_ID);
                            String token = UserUtil.getInstance().getToken();// (String) CacheManager.getInstance().getCachedObject(Constants.TOKEN);

                            body.put(Constants.ID, userID);
                            body.put(Constants.TOKEN, token);
                            body.put(Constants._METHOD, Constants._METHOD_PATCH);
                            body.put(Constants.NAME, userName);
                            body.put(Constants.EMAIL, email);
                            body.put(Constants.PASSWORD, password);
                            body.put(Constants.CITY, cityId);
                            Logger.d("CityID", cityId + "");
//                    String image = volley.getReducedStringImagePNG(volley.getBitmap(ivAccountPhoto));
                            body.put(Constants.IMAGE, image);
                            Logger.d("Image", image + "Image");

                            service.getResponse(Request.Method.POST, Constants.USER_UPDATE, body, new Service.ServiceResponse() {
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
                        setupUser();
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
}
