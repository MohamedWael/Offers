package com.sunmediaeg.offers.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.activities.MainActivity;
import com.sunmediaeg.offers.activities.OffersGeneralActivity;
import com.sunmediaeg.offers.utilities.Constants;
import com.sunmediaeg.offers.utilities.Logger;
import com.sunmediaeg.offers.utilities.SharedPreferencesManager;
import com.sunmediaeg.offers.views.TimerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginChoiceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginChoiceFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Button btnSignUpAsVendor, btnSignUpAsUser, btnLogin;
    private Intent intent;
    private Intent mainActivityIntent;

    public LoginChoiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginChoiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginChoiceFragment newInstance(String param1, String param2) {
        LoginChoiceFragment fragment = new LoginChoiceFragment();
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
        View view = inflater.inflate(R.layout.fragment_login_choice, container, false);
        haveAccount();
        initComponents(view);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSignUpAsVendor:
                startActivity(Constants.ACTIVITY_SIGN_UP_AS_VENDOR);
                break;
            case R.id.btnSignUpAsUser:
                startActivity(Constants.ACTIVITY_SIGN_UP_AS_USER);
                break;
            case R.id.btnLogin:
                startActivity(Constants.ACTIVITY_LOGIN);
                break;
        }

    }

    private void startActivity(int value) {
        intent.putExtra(Constants.ACTIVITY, value);
        startActivity(intent);
//        getActivity().finish();
    }

    private void initComponents(View v) {
        testTimerView(v);
        intent = new Intent(getActivity(), OffersGeneralActivity.class);
        btnSignUpAsVendor = (Button) v.findViewById(R.id.btnSignUpAsVendor);
        btnSignUpAsUser = (Button) v.findViewById(R.id.btnSignUpAsUser);
        btnLogin = (Button) v.findViewById(R.id.btnLogin);
        btnSignUpAsVendor.setOnClickListener(this);
        btnSignUpAsUser.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void testTimerView(View v) {
        TimerView timerView = (TimerView) v.findViewById(R.id.timerView);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        try {
            Logger.d("TimerView","called");
            timerView.setStartDate(simpleDateFormat.parse("2017-3-2 11:50:50").getTime());
            timerView.setEndDate(simpleDateFormat.parse("2017-3-4 11:50:50").getTime());

        } catch (ParseException e) {
            e.printStackTrace();
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


    private void haveAccount() {
        boolean haveAccount = SharedPreferencesManager.getInstance(getContext()).initSharedPreferences().getBoolean(Constants.HAVE_ACCOUNT, false);
        Logger.d("HaveAccount?", haveAccount + "");
        if (haveAccount) {
            mainActivityIntent = new Intent(getActivity(), MainActivity.class);
            mainActivityIntent.putExtra(Constants.IS_COMPANY_PROFILE, false); // <-- this extra is related only to the MainActivity
            getActivity().startActivity(intent);
            getActivity().finish();
        }
    }
}
