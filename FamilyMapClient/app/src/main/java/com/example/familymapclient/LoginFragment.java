package com.example.familymapclient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Model.Event;
import Model.Person;
import Model.User;
import Request.UserLoginRequest;
import Request.UserRegisterRequest;
import Response.EventsResponse;
import Response.PersonsResponse;
import Response.UserLoginResponse;
import Response.UserRegisterResponse;

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
    private static final String LOG_TAG = "LoginFragment";
    private static final String USER_KEY = "userKey";
    private static final String PERSONS_KEY = "personsKey";
    private static final String EVENTS_KEY = "eventsKey";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private User newUser = new User("", "", "", "", "", "", "");
    private String Host = "";
    private String Port = "";
    private Button mLoginButton;
    private Button mRegisterButton;
    private PersonsResponse pRes;
    private EventsResponse eRes;
    private UserLoginResponse ulRes;

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
        EditText mHost = (EditText) v.findViewById(R.id.server_host_hint);
        Host = mHost.getText().toString();
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

        EditText mPort = (EditText) v.findViewById(R.id.server_port_hint);
        Port = mPort.getText().toString();
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

        EditText mUsername = (EditText) v.findViewById(R.id.username_hint);
        newUser.setUsername(mUsername.getText().toString());
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

        EditText mPassword = (EditText) v.findViewById(R.id.password_hint);
        newUser.setPassword(mPassword.getText().toString());
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

        EditText mFirstName = (EditText) v.findViewById(R.id.first_name_hint);
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

        EditText mLastName = (EditText) v.findViewById(R.id.last_name_hint);
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

        EditText mEmail = (EditText) v.findViewById(R.id.email_hint);
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
        RadioGroup mGender = (RadioGroup) v.findViewById(R.id.genderGroup);
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
        checkNeededButtonInfo();

        return v;
    }

    public void checkNeededButtonInfo() {
        //login
        if (!Host.isEmpty() && !Port.isEmpty() && !newUser.getUsername().isEmpty() && !newUser.getPassword().isEmpty()) {
            mLoginButton.setEnabled(true);
            mLoginButton.setOnClickListener(v -> {
                try {
                    Handler uiThreadMessageHandler = new Handler() {
                        @Override
                        public void handleMessage(Message message) {
                            Bundle bundle = message.getData();
                            String userResult = bundle.getString(USER_KEY, null);
                            ulRes = new Gson().fromJson(userResult, UserLoginResponse.class);
                            String personResult = bundle.getString(PERSONS_KEY, null);
                            if (personResult != null)
                            {
                                pRes = new Gson().fromJson(personResult, PersonsResponse.class);
                                String eventResult = bundle.getString(EVENTS_KEY, null);
                                eRes = new Gson().fromJson(eventResult, EventsResponse.class);
                                if (pRes.success == true)
                                {
                                    switchFragments(); //switches to the map fragment
                                }
                            }
                            else
                            {
                                Toast.makeText(getActivity(), R.string.login_fail_toast,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    };

                    LoginTask loginTask = new LoginTask
                            (uiThreadMessageHandler,
                            new URL("http", Host, Integer.parseInt(Port), "user/login"),
                            new UserLoginRequest(newUser.getUsername(), newUser.getPassword())
                            );
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(loginTask);
                } catch (MalformedURLException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                }
            });
            //allow register
            if (!newUser.getFirstName().isEmpty() && !newUser.getLastName().isEmpty() && !newUser.getEmail().isEmpty() && !newUser.getGender().isEmpty()) {
                mRegisterButton.setEnabled(true);
                mRegisterButton.setOnClickListener(v -> {
                  try {
                    Handler uiThreadMessageHandler = new Handler() {
                        @Override
                        public void handleMessage(Message message) {
                            Bundle bundle = message.getData();
                            String userResult = bundle.getString(USER_KEY, null);
                            ulRes = new Gson().fromJson(userResult, UserLoginResponse.class);
                            String personResult = bundle.getString(PERSONS_KEY, null);
                            if (personResult != null)
                            {
                            pRes = new Gson().fromJson(personResult, PersonsResponse.class);
                            String eventResult = bundle.getString(EVENTS_KEY, null);
                            eRes = new Gson().fromJson(eventResult, EventsResponse.class);
                                switchFragments(); //switches to the map fragment
                            }
                            else
                            {
                                Toast.makeText(getActivity(), R.string.register_fail_toast,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    };

                    RegisterTask registerTask = new RegisterTask
                            (uiThreadMessageHandler,
                            new URL("http", Host, Integer.parseInt(Port), "user/register"),
                            new UserRegisterRequest(newUser.getUsername(), newUser.getPassword(),
                            newUser.getEmail(), newUser.getFirstName(), newUser.getLastName(), newUser.getGender())
                            );
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(registerTask);
                } catch (MalformedURLException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                }
                });
            }
            else
            {
                mRegisterButton.setEnabled(false);
            }
        }
        else
        {
            mLoginButton.setEnabled(false);
        }
    }

    public void cacheData()
    {
        Datacache instance = Datacache.getInstance();
        instance.setAuthToken(ulRes.getAuthtoken());
        instance.setUser(pRes.getPerson(ulRes.getPersonID()));
        instance.getPersonsMap().put(instance.getUser().getPersonID(), instance.getUser()); //put the user's person into the map
        //now that we have the user's person and authToken we can set their family data
        setImmediateFamilyMembers(instance); //immediate family
        setFamilyMembers(instance, instance.getUser(), true);
        //a map of personIds to a set of associated events
        generateEventMap(instance);
    }

    public void generateEventMap(Datacache instance)
    {
        for (Event e : eRes.getData())
        {
            if (instance.getEventsMap().containsKey(e.getPersonID()))
            {
                instance.getEventsMap().get(e.getPersonID()).add(e);
            }
            else
            {
                Set<Event> eventSet = new HashSet<Event>();
                eventSet.add(e);
                instance.getEventsMap().put(e.getPersonID(), eventSet);
            }
        }
    }

    //get the father and then go through and get the father's father etc until it is null
    //pass in a user will need to be recursive
    public void setFamilyMembers(Datacache instance, Person person, boolean fSide)
    {
        if (person.getFatherID() != null) {
            Person father = pRes.getPerson(person.getFatherID());
            instance.getPersonsMap().put(father.getPersonID(), father);
            Person mother = pRes.getPerson(person.getMotherID());
            instance.getPersonsMap().put(mother.getPersonID(), mother);
            if (fSide) {
                instance.getFatherSideMales().add(father);
                instance.getFatherSideFemales().add(mother);
                setFamilyMembers(instance, father, true);
                setFamilyMembers(instance, mother, true);
            } else {
                instance.getMotherSideMales().add(father);
                instance.getMotherSideFemales().add(mother);
                setFamilyMembers(instance, father, false);
                setFamilyMembers(instance, mother, false);
            }
        }
    }

    //set immediate family
    public void setImmediateFamilyMembers(Datacache instance)
    {
        Person user = instance.getUser();
        if (user.getGender() == "m")
            instance.getImmediateFamilyMales().add(user);
        else
            instance.getImmediateFamilyFemales().add(user);
        //parents
        instance.getImmediateFamilyMales().add(pRes.getPerson(user.getFatherID())); //father
        instance.getImmediateFamilyMales().add(pRes.getPerson(user.getMotherID())); //mother
        //spouse??
        if (user.getSpouseID() != null)
        {
            Person spouse = pRes.getPerson(user.getSpouseID());
            if (spouse.getGender() == "m")
                instance.getImmediateFamilyMales().add(user);
            else
                instance.getImmediateFamilyFemales().add(user);
        }
    }


    //creates an instance of map fragment and switches to it
    public void switchFragments()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment mapFragment = new MapFragment();
        fm.beginTransaction().replace(R.id.fragment_container, mapFragment).commit();
    }

}
