package com.mowael.offers.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;
import com.mowael.offers.R;
import com.mowael.offers.activities.MainActivity;
import com.mowael.offers.dataModel.APIResponse;
import com.mowael.offers.dataModel.cities.CitiesResponse;
import com.mowael.offers.dataModel.cities.City;
import com.mowael.offers.dataModel.jsonModels.FbProfileData;
import com.mowael.offers.dataModel.jsonModels.LoginResponse;
import com.mowael.offers.utilities.ApiError;
import com.mowael.offers.utilities.CacheManager;
import com.mowael.offers.utilities.Constants;
import com.mowael.offers.utilities.Logger;
import com.mowael.offers.utilities.Service;
import com.mowael.offers.utilities.SharedPreferencesManager;
import com.mowael.offers.utilities.SignUpUtility;
import com.mowael.offers.utilities.Toaster;
import com.mowael.offers.utilities.UserUtil;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class SignUpFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tvTitle, tvNotification;
    private ImageButton ibBack;
    private EditText etUserName, etMail, etPassword;
    private String userName, mail, password;
    private Button btnSend, btnFaceBook;
    private ImageButton ibFaceBook, ibTwitter, ibGooglePlus;
    private ProgressBar progressBar;
    private Service requests;
    private SharedPreferencesManager prefs;
    private SharedPreferences.Editor editor;
    private CallbackManager callbackManager;
    private Profile profile;
    private Gson gson;
    private FbProfileData fbProfileData;
    private TwitterAuthClient twitterAuthClient;
    private String token;
    private String secret;
    private CacheManager manager;
    private Spinner sCities;
    private ArrayList<String> cities;
    private ArrayAdapter<String> spinnerAdapter;

    private int cityId = 1;
//    private com.sunmediaeg.offers.utilities.TwitterLoginButton btnTwitterLogin;


    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        manager = CacheManager.getInstance();
        gson = new Gson();
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();

                Logger.d("AccessToken", loginResult.getAccessToken().getToken());
                Logger.d("grantedPermissions", loginResult.getRecentlyGrantedPermissions().toString());
//                profile = Profile.getCurrentProfile();
//                Logger.e("profileName", profile.getName());
//                etUserName.setText(profile.getName());


                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Logger.d("GraphResponse", response.toString());
                        Logger.d("JSONObject", object.toString());
                        fbProfileData = gson.fromJson(object.toString(), FbProfileData.class);
                        etUserName.setText(fbProfileData.getName());
                        etMail.setText(fbProfileData.getEmail());
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

            }

            @Override
            public void onCancel() {
                Logger.e("FbOnCancel", "canceled");
            }

            @Override
            public void onError(FacebookException error) {
                Logger.e("fbError", error.toString());
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initComponents(view);
        UserUtil.getInstance().init(getContext());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initComponents(View v) {

        prefs = SharedPreferencesManager.getInstance(getContext());
        editor = prefs.initEditor();
        requests = Service.getInstance(getContext());
        Constants.hideSearchButton(v);
        tvNotification = (TextView) v.findViewById(R.id.tvNotification);

        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        tvTitle.setText(mParam2);

        etUserName = (EditText) v.findViewById(R.id.etEmail);
        etMail = (EditText) v.findViewById(R.id.etMail);
        etPassword = (EditText) v.findViewById(R.id.etPassword);

        ibBack = (ImageButton) v.findViewById(R.id.ibBack);
        ibTwitter = (ImageButton) v.findViewById(R.id.ibTwitter);
        btnFaceBook = (Button) v.findViewById(R.id.btnFaceBook);
//        btnTwitterLogin = (com.sunmediaeg.offers.utilities.TwitterLoginButton) v.findViewById(R.id.btnTwitterLogin);
//        btnTwitterLogin.setBackgroundResource(R.drawable.twitter);
        ibGooglePlus = (ImageButton) v.findViewById(R.id.ibGooglePlus);
        btnSend = (Button) v.findViewById(R.id.btnSignUp);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        sCities = (Spinner) v.findViewById(R.id.sCities);
        cities = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, cities);

        sCities.setAdapter(spinnerAdapter);

        getCities();

        ibBack.setOnClickListener(this);
        btnFaceBook.setOnClickListener(this);
        ibTwitter.setOnClickListener(this);
        ibGooglePlus.setOnClickListener(this);
        btnSend.setOnClickListener(this);


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager != null)
            callbackManager.onActivityResult(requestCode, resultCode, data);
        Logger.d("OnActivityResult", "checked");
        if (twitterAuthClient != null)
            twitterAuthClient.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ibBack:
                getActivity().finish();
                break;
            case R.id.btnSignUp:
                try {
                    tvNotification.setText("");
                    userName = etUserName.getText().toString();
                    mail = etMail.getText().toString();
                    password = etPassword.getText().toString();

                    if (userName.toCharArray().length >= 6 && userName.toCharArray().length <= 255 &&
                            password.toCharArray().length >= 6 && password.toCharArray().length <= 255
                            && !mail.isEmpty()) {
                        SignUpUtility signUp = SignUpUtility.newInstance(userName, mail, password);
                        if (!SignUpUtility.isFirstTime()) {
                            signUp.setUserName(userName);
                            signUp.setEmail(mail);
                            signUp.setPassword(password);
                        }

                        JSONObject body = new JSONObject();
                        body.put(Constants.NAME, signUp.getUserName());
                        body.put(Constants.EMAIL, signUp.getEmail());
                        body.put(Constants.PASSWORD, signUp.getPassword());
                        body.put(Constants.CITY, cityId);
//                        body.put(Constants._METHOD, Constants._METHOD_POST);
                        String url = "";
                        if (mParam1.equals(Constants.USER)) {
                            url = Constants.USER.substring(0, Constants.USER.length() - 1);
                        } else {
                            url = Constants.REGISTER_VENDOR;
                        }
                        final String finalUrl = url;
                        Logger.d("Register", url + "");
                        editor.putString(Constants.PASSWORD, password);
                        //NOTE Registration as a vendor is removed form the backend
                        progressBar.setVisibility(View.VISIBLE);
                        requests.getResponse(Request.Method.POST, url, body, new Service.ServiceResponse() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Logger.d("response", response.toString());
                                try {
                                    int responseCode = response.getInt("code");

                                    APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                                    if (responseCode == 200) {
                                        LoginResponse loginResponse = gson.fromJson(response.toString(), LoginResponse.class);
                                        loginResponse.getData().getUser().setUserType(Constants.TYPE_USER);
                                        Logger.d("user", loginResponse.getData().getUser().toString());

                                        UserUtil.getInstance().saveUser(loginResponse.getData().getUser().getName(),
                                                loginResponse.getData().getUser().getEmail(),
                                                loginResponse.getData().getUser().getId(),
                                                loginResponse.getData().getUser().getToken());

                                        manager.cacheObject(Constants.NAME, loginResponse.getData().getUser().getName());
                                        manager.cacheObject(Constants.EMAIL, loginResponse.getData().getUser().getEmail());
                                        manager.cacheObject(Constants.TOKEN, loginResponse.getData().getUser().getToken());
                                        manager.cacheObject(Constants.USER_ID, loginResponse.getData().getUser().getId());
                                        manager.cacheObject(Constants.HAVE_ACCOUNT, true);
                                        Intent intent = new Intent(SignUpFragment.this.getActivity(), MainActivity.class);
                                        intent.putExtra(Constants.IS_COMPANY_PROFILE, false); // <-- this extra is related only to the MainActivity
//                                        Toaster.getInstance().toast("تم إنشاء الحساب بنجاح");
                                        SignUpFragment.this.getActivity().startActivity(intent);
                                        SignUpFragment.this.getActivity().finish();
                                    } else {
                                        String emailMsg = "", passwordMsg = "";
//                                        int responseCode = response.getInt("code");
                                        if (response.has("errors")) {
                                            JSONObject errors = response.getJSONObject("errors");
                                            if (errors.has("email")) {
                                                emailMsg = errors.getString("email");
                                                Toaster.getInstance().toast(emailMsg);
                                            } else if (errors.has("password")) {
                                                passwordMsg = errors.getString("password");
                                                Toaster.getInstance().toast(passwordMsg);
                                            } else {
                                                ApiError apiError = new ApiError(responseCode);
                                                Toaster.getInstance().toast(apiError.getErrorMsg());
                                            }
                                        }

                                        tvNotification.setText(emailMsg + "\n" + passwordMsg);
//                                        Constants.toastMsg(getContext(), emailMsg + "\n" + passwordMsg);
                                    }

                                    progressBar.setVisibility(View.INVISIBLE);
                                } catch (Exception e) {
//                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                editor.putBoolean(Constants.HAVE_ACCOUNT, false);
                                editor.commit();
                                Logger.d("VolleyError", error.toString());
                                if (error.getMessage() != null)
                                    Logger.d("VolleyError", error.getMessage());
                                progressBar.setVisibility(View.INVISIBLE);

                            }

                            @Override
                            public void updateUIOnNetworkUnavailable(String noInternetMessage) {
                                tvNotification.setText(noInternetMessage);
                            }
                        });

                    } else {
                        tvNotification.setText("يجب ألا يقل إسم المستخدم وكلمة السر عن 6 أحرف");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.e("SignUpError", e.getMessage());
                    tvNotification.setText("من فضلك تأكد من إدخال جميع الحقول بشكل صحيح");
                }

                break;
            case R.id.btnFaceBook:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile", "user_photos"));
                break;
            case R.id.ibTwitter:
                twitterAuthClient = new TwitterAuthClient();
                twitterAuthClient.authorize(getActivity(), new Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> result) {
                        Logger.d("TwitterAuth", "Successed");
                        Logger.d("TwitterAuth", result.data.getUserName());
                        TwitterSession session = Twitter.getSessionManager().getActiveSession();
                        TwitterAuthToken authToken = session.getAuthToken();
                        token = authToken.token;
                        secret = authToken.secret;
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Logger.d("TwitterAuth", "fail");
                        Logger.d("TwitterAuth", exception.getMessage());

                    }
                });
                break;
            case R.id.ibGooglePlus:

                break;

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
}
