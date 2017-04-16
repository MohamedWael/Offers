package com.sunmediaeg.offers.fragment;

import android.content.Context;
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
import com.google.gson.Gson;
import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.dataModel.APIResponse;
import com.sunmediaeg.offers.utilities.Constants;
import com.sunmediaeg.offers.utilities.Service;
import com.sunmediaeg.offers.utilities.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ForgetPasswordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ForgetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgetPasswordFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TextView tvTitle;
    private ImageButton ibBack;
    private EditText etMail;
    private Button btnSignUp;
    private TextView tvNotification;
    private ProgressBar progressBar;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgetPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgetPasswordFragment newInstance(String param1, String param2) {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        initComponent(view);
        return view;
    }

    private void initComponent(View v) {
        Constants.hideSearchButton(v);

        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        tvTitle.setText(mParam1);
        ibBack = (ImageButton) v.findViewById(R.id.ibBack);
        etMail = (EditText) v.findViewById(R.id.etMail);
        tvNotification = (TextView) v.findViewById(R.id.tvNotification);
        btnSignUp = (Button) v.findViewById(R.id.btnSignUp);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        ibBack.setOnClickListener(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etMail.getText().toString();
                if (VolleySingleton.isValidEmail(email)) {
                    getPassword(Constants.FORGET_PASSWORD, email);
                    showNotification(false);
                } else {
                    setNotificationMsg(getString(R.string.tvNotification));
                }
            }
        });
    }

    private void setNotificationMsg(String msg) {
        tvNotification.setText(msg);
        showNotification(true);
    }

    private void showNotification(boolean show) {
        tvNotification.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    private void getPassword(String url, String email) {
        JSONObject body = new JSONObject();
        try {
            showProgress(true);
            body.put(Constants.EMAIL, email);
            Service.getInstance(getContext()).getResponse(Request.Method.POST, url, body, new Service.ServiceResponse() {
                @Override
                public void onResponse(JSONObject response) {
                    showProgress(false);
                    Gson gson = new Gson();
                    APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                    if (apiResponse.isSuccess()) {
                        setNotificationMsg(getString(R.string.tvNotificationResponse));
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgress(false);
                }

                @Override
                public void updateUIOnNetworkUnavailable(String noInternetMessage) {
                    showProgress(false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
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
