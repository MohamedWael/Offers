package com.sunmediaeg.offers.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.activities.MainActivity;
import com.sunmediaeg.offers.dataModel.jsonModels.LoginResponse;
import com.sunmediaeg.offers.utilities.ApiError;
import com.sunmediaeg.offers.utilities.Service;
import com.sunmediaeg.offers.utilities.Constants;
import com.sunmediaeg.offers.utilities.Logger;
import com.sunmediaeg.offers.utilities.SharedPreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
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

    private ProgressBar progressBar;
    private EditText etUserName, etPassword;
    private String email, password;

    private Button btnForgetPassword, btnSend, btnHaveAccoutn;
    private SharedPreferencesManager preferencesManager;
    private SharedPreferences pref;
    private Service requests;
    private SharedPreferences.Editor editor;
    private SignUpFragment signUpFragment;
    private ForgetPasswordFragment forgetPassworsFragment;

    public LoginFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initComponents(view);

        return view;
    }

    private void initComponents(View v) {
        requests = Service.getInstance(getContext());
        preferencesManager = SharedPreferencesManager.getInstance(getContext());
        pref = preferencesManager.initSharedPreferences();
        editor = preferencesManager.initEditor();
        Constants.hideSearchButton(v);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        tvNotification = (TextView) v.findViewById(R.id.tvNotification);
        tvTitle.setText(mParam1);
        ibBack = (ImageButton) v.findViewById(R.id.ibBack);
        etUserName = (EditText) v.findViewById(R.id.etEmail);
        etPassword = (EditText) v.findViewById(R.id.etPassword);
        btnForgetPassword = (Button) v.findViewById(R.id.btnForgetPassword);
        btnSend = (Button) v.findViewById(R.id.btnSend);
        btnHaveAccoutn = (Button) v.findViewById(R.id.btnHaveAccoutn);

        ibBack.setOnClickListener(this);
        btnForgetPassword.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        btnHaveAccoutn.setOnClickListener(this);

        btnHaveAccoutn.setOnTouchListener(this);
        btnForgetPassword.setOnTouchListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibBack:
                getActivity().finish();
                break;
            case R.id.btnForgetPassword:
                forgetPassworsFragment = ForgetPasswordFragment.newInstance(getString(R.string.btnForgetPassword), "");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flLogin, forgetPassworsFragment).commit();
                break;
            case R.id.btnSend:
                try {
                    tvNotification.setText("");
                    progressBar.setVisibility(View.VISIBLE);
                    email = etUserName.getText().toString();
                    password = etPassword.getText().toString();

                    if (password.toCharArray().length >= 6 && password.toCharArray().length <= 255
                            && email.matches(Constants.REGEX_Mail)) {
                        JSONObject body = new JSONObject();
                        body.put(Constants.EMAIL, email);
                        body.put(Constants.PASSWORD, password);
                        requests.getResponse(Request.Method.POST, Constants.USER_LOGIN, body, new Service.ServiceResponse() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String emailMsg = "", passwordMsg = "", name = "";
                                    int responseCode = 0;
                                    responseCode = response.getInt("code");

                                    if (response.has("errors")) {
                                        JSONObject errors = response.getJSONObject("errors");
                                        if (errors.has("email")) {
                                            emailMsg = errors.getString("email");
                                        } else if (errors.has("password")) {
                                            passwordMsg = errors.getString("password");
                                        } else if (errors.has("name")) {
                                            name = errors.getString("name");
                                        } else {
                                            ApiError apiError = new ApiError(responseCode);
                                            Constants.toastMsg(getContext(), apiError.getErrorMsg());
                                        }
                                    }
                                    if (responseCode == 200) {
                                        Logger.d("LoginResponse", response.toString());
                                        Gson gson = new Gson();
                                        LoginResponse login = gson.fromJson(response.toString(), LoginResponse.class);

                                        editor.putString(Constants.NAME, login.getData().getUser().getName());
                                        editor.putString(Constants.EMAIL, login.getData().getUser().getEmail());
                                        editor.putLong(Constants.USER_ID, login.getData().getUser().getId());
                                        editor.putBoolean(Constants.HAVE_ACCOUNT, true);
                                        editor.commit();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        intent.putExtra(Constants.IS_COMPANY_PROFILE, false); // <-- this extra is related only to the MainActivity
                                        getActivity().startActivity(intent);
                                        getActivity().finish();
                                        progressBar.setVisibility(View.INVISIBLE);
                                    } else {
                                        Constants.toastMsg(getContext(), name + "\n" + emailMsg + "\n" + passwordMsg);
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Logger.d("LoginError", error.toString());
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                        preferencesManager.initEditor().putBoolean(Constants.HAVE_ACCOUNT, true).commit();
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        String msg = getString(R.string.loginError);
                        tvNotification.setText(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    String msg = getString(R.string.loginError2);
                    tvNotification.setText(msg);
                }

                break;
            case R.id.btnHaveAccoutn:
                signUpFragment = SignUpFragment.newInstance(Constants.REGISTER_USER, getString(R.string.tvCreateAccount));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flLogin, signUpFragment).commit();
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

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {

        switch (v.getId()) {
            case R.id.btnForgetPassword:
                touchListener(LoginFragment.this.btnForgetPassword, motionEvent);
                break;
            case R.id.btnHaveAccoutn:
                touchListener(LoginFragment.this.btnHaveAccoutn, motionEvent);
                break;
        }
        return false;
    }


    private void touchListener(Button v, MotionEvent motionEvent) {
        Logger.d("action", motionEvent.getAction() + "");
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN ||
                motionEvent.getAction() == MotionEvent.ACTION_HOVER_ENTER ||
                motionEvent.getAction() == MotionEvent.ACTION_HOVER_MOVE ||
                motionEvent.getAction() == MotionEvent.ACTION_BUTTON_PRESS ||
                motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            v.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBackground));
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP ||
                motionEvent.getAction() == MotionEvent.ACTION_HOVER_EXIT ||
                motionEvent.getAction() == MotionEvent.ACTION_BUTTON_RELEASE ||
                motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE ||
                motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
            v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
            v.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        } else {
            v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            v.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBackground));
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
