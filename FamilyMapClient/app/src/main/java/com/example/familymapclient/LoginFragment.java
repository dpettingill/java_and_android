package com.example.familymapclient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import Model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private User newUser = new User();
    private String Host;
    private String Port;
    private Button mLoginButton;
    private Button mRegisterButton;
    private EditText mHost, mPort, mUsername, mPassword, mFirstName, mLastName, mEmail;
    private RadioGroup mGender;

    public LoginFragment() {
        // Required empty public constructor
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
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        // USER STUFF //
        mHost = (EditText) v.findViewById(R.id.server_host_hint);
        mHost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //this space is intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Host = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkNeededButtonInfo();
            }
        });

        mPort = (EditText) v.findViewById(R.id.server_port_hint);
        mPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Port = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkNeededButtonInfo();
            }
        });

        mUsername = (EditText) v.findViewById(R.id.username_hint);
        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newUser.setUsername((s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkNeededButtonInfo();
            }
        });

        mPassword = (EditText) v.findViewById(R.id.password_hint);
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newUser.setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkNeededButtonInfo();
            }
        });

        mFirstName = (EditText) v.findViewById(R.id.first_name_hint);
        mFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newUser.setFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkNeededButtonInfo();
            }
        });

        mLastName = (EditText) v.findViewById(R.id.last_name_hint);
        mLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newUser.setLastName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkNeededButtonInfo();
            }
        });

        mEmail = (EditText) v.findViewById(R.id.email_hint);
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newUser.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkNeededButtonInfo();
            }
        });

        // BUTTONS //
        mGender = (RadioGroup) v.findViewById(R.id.genderGroup);
        mGender.clearCheck();
        mGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.gender_male) {
                    newUser.setGender("m");
                } else if (checkedId == R.id.gender_female) {
                    newUser.setGender("f");
                }
                checkNeededButtonInfo();
            }
        });

        mLoginButton = (Button) v.findViewById(R.id.login_button);
        mLoginButton.setEnabled(false);
        mRegisterButton = (Button) v.findViewById(R.id.register_button);
        mRegisterButton.setEnabled(false);
        //instead build a helper function and call it in all of the text changed things. Then if we hit our criteria update the button(s)
        //allow login

        return v;
    }

    public void checkNeededButtonInfo() {
        if (Host != null && Port != null && newUser.getUsername() != null && newUser.getPassword() != null) {
            mLoginButton.setEnabled(true);
            mLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(MainActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
                }
            });
            //allow register
            if (newUser.getFirstName() != null && newUser.getLastName() != null && newUser.getEmail() != null && newUser.getGender() != null) {
                mRegisterButton.setEnabled(true);
                mRegisterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                Toast.makeText(MainActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}