package com.example.irrigationmanegmentapp;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.irrigationmanegmentapp.Finals.CLEAR_SKY_DESCRIPTION;
import static com.example.irrigationmanegmentapp.Finals.CLOUDY_DAY_DESCRIPTION;
import static com.example.irrigationmanegmentapp.Finals.RAINNY_DAY_DESCRIPTION;

public class ProfileActivity extends AppCompatActivity {

    private TextView main_TXT_temp, main_TXT_weatherDesc, main_TXT_userName;
    private ImageView main_IMG_weatherIcon;
    private DatabaseReference mUserProfile;
    private String mobileNumber;
    private User mUser;
    private UserDBManager mUserDBManager;
    private boolean setProfileAlready;
    private WeatherAPIManager mWeatherAPIManager;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setProfileAlready = false;
        //Get the last known location from Google-play Service
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        Intent intent = getIntent();
        mUserDBManager = new UserDBManager(getApplicationContext());
        mobileNumber = intent.getStringExtra(Finals.MOBILE_NUMBER);
        //Get the user object from FireBse using his mobile number
        mUserProfile = FirebaseDatabase.getInstance().getReference("Users").child(mobileNumber);
        mUserProfile.addListenerForSingleValueEvent(readUserProfileFromDataBase);

    }

    /**
     *Creates the main fragment were all the valves are displayed
     */
    private void startValveFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment valveFragment = new vavlesFragment(mUser, ProfileActivity.this, dialogCallBack);
        ft.replace(R.id.fragment_container, valveFragment).commit();
    }


    /**
     * Read from Firebase listener
     */
    ValueEventListener readUserProfileFromDataBase = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            mUser = dataSnapshot.getValue(User.class);
            //Initialize the views, main layout and fragment in 'OnDataChange' because of the Async task
            findViews();
            setProfileLayout();
            startValveFragment();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    /**
     * Refresh the main fragment after adding new valve or removing valves
     */
    dialogCallBack dialogCallBack = new dialogCallBack() {
        @Override
        public void refresh() {
            startValveFragment();
            mUserDBManager.updateUser(mUser);

        }
    };

    /**
     * Find views
     */
    private void findViews() {
        main_TXT_temp = findViewById(R.id.main_TXT_temp);
        main_TXT_weatherDesc = findViewById(R.id.main_TXT_weatherDesc);
        main_IMG_weatherIcon = findViewById(R.id.main_IMG_weatherIcon);
        main_TXT_userName = findViewById(R.id.main_TXT_userName);
    }

    /**
     * Sets the upper part of the activity.
     * Display the current user's name and update the current weather components using simple API request(weather icon, temperature, short description)
     */
    private void setProfileLayout() {
        if (mUser != null) {
            main_TXT_userName.setText("Hello " + mUser.getFirstName() + "!");
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                //
                                mWeatherAPIManager = new WeatherAPIManager(getApplicationContext(), weatherComponentsSetters, location.getLatitude(), location.getLongitude());

                            }
                        }
                    });
        }
    }


    /**
     * Callback from the WeaterAPI manager to set the layout according the weather
     */
    WeatherComponentsSetters weatherComponentsSetters = new WeatherComponentsSetters() {
        @Override
        public void tempSetter(int temp) {
            main_TXT_temp.setText(temp + " \u2103");
        }

        @Override
        public void dayDescriptionSetter(int code) {
            if (code <= 802 && code >= 800) {
                main_TXT_weatherDesc.setText(CLEAR_SKY_DESCRIPTION);
            } else if (code <= 522 && code >= 200) {
                main_TXT_weatherDesc.setText(RAINNY_DAY_DESCRIPTION);
            } else if (code >= 803 && code <= 804) {
                main_TXT_weatherDesc.setText(CLOUDY_DAY_DESCRIPTION);
            } else {
                main_TXT_weatherDesc.setText("Unknown Precipitation");
            }
        }

        @Override
        public void imageViewSetter(int code) {
            if (code <= 802 && code >= 800) {
                main_IMG_weatherIcon.setImageResource(R.drawable.ic_sun);
            } else if (code <= 522 && code >= 200) {
                main_IMG_weatherIcon.setImageResource(R.drawable.ic_drop);
            } else if (code >= 803 && code <= 804) {
                main_IMG_weatherIcon.setImageResource(R.drawable.ic_clouds);
            } else {
                main_IMG_weatherIcon.setImageResource(R.drawable.ic_clouds);
            }
        }
    };
}
