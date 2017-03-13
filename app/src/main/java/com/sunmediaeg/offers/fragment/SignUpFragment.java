package com.sunmediaeg.offers.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.activities.MainActivity;
import com.sunmediaeg.offers.dataModel.jsonModels.FbProfileData;
import com.sunmediaeg.offers.dataModel.jsonModels.LoginResponse;
import com.sunmediaeg.offers.utilities.ApiError;
import com.sunmediaeg.offers.utilities.Constants;
import com.sunmediaeg.offers.utilities.Logger;
import com.sunmediaeg.offers.utilities.Service;
import com.sunmediaeg.offers.utilities.SharedPreferencesManager;
import com.sunmediaeg.offers.utilities.SignUpUtility;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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

        ibBack.setOnClickListener(this);
        btnFaceBook.setOnClickListener(this);
        ibTwitter.setOnClickListener(this);
        ibGooglePlus.setOnClickListener(this);
        btnSend.setOnClickListener(this);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Logger.d("OnActivityResult", "checked");
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
                            password.toCharArray().length >= 6 && password.toCharArray().length <= 255) {
                        progressBar.setVisibility(View.VISIBLE);
                        SignUpUtility signUp = SignUpUtility.getInstance(userName, mail, password);
                        if (!SignUpUtility.isFirstTime()) {
                            signUp.setUserName(userName);
                            signUp.setEmail(mail);
                            signUp.setPassword(password);
                        }

                        JSONObject body = new JSONObject();
                        body.put(Constants.NAME, signUp.getUserName());
                        body.put(Constants.EMAIL, signUp.getEmail());
                        body.put(Constants.PASSWORD, signUp.getPassword());
                        String url = "";
                        if (mParam1.equals(Constants.USER)) {
                            url = Constants.USER;
                        } else {
                            url = Constants.REGISTER_VENDOR;
                        }
                        final String finalUrl = url;
                        Logger.d("Register", url + "");
                        //NOTE Registration as a vendor is removed form the backend
                        requests.getResponse(Request.Method.POST, url, body, new Service.ServiceResponse() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Logger.d("response", response.toString());
                                try {
                                    String emailMsg = "", passwordMsg = "";
                                    int responseCode = response.getInt("code");
                                    if (response.has("errors")) {
                                        JSONObject errors = response.getJSONObject("errors");
                                        if (errors.has("email")) {
                                            emailMsg = errors.getString("email");
                                        } else if (errors.has("password")) {
                                            passwordMsg = errors.getString("password");
                                        } else {
                                            ApiError apiError = new ApiError(responseCode);
                                            Constants.toastMsg(getContext(), apiError.getErrorMsg());
                                        }
                                    }

                                    if (responseCode == 200) {
                                        LoginResponse loginResponse = gson.fromJson(response.toString(), LoginResponse.class);

                                        if (finalUrl.equals(Constants.USER)) {
                                            loginResponse.getData().getUser().setUserType(Constants.TYPE_USER);
                                        } else {
                                            loginResponse.getData().getUser().setUserType(Constants.TYPE_VENDOR);
                                        }
                                        Logger.d("user", loginResponse.getData().getUser().toString());
                                        editor.putString(Constants.NAME, loginResponse.getData().getUser().getName());
                                        editor.putString(Constants.EMAIL, loginResponse.getData().getUser().getEmail());
                                        editor.putLong(Constants.USER_ID, loginResponse.getData().getUser().getId());
                                        editor.putBoolean(Constants.HAVE_ACCOUNT, true);
                                        editor.commit();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        intent.putExtra(Constants.IS_COMPANY_PROFILE, false); // <-- this extra is related only to the MainActivity
                                        getActivity().startActivity(intent);
                                        getActivity().finish();
                                        progressBar.setVisibility(View.INVISIBLE);
                                    } else {
                                        Constants.toastMsg(getContext(), emailMsg + "\n" + passwordMsg);
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Logger.d("VolleyError", error.toString());
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
