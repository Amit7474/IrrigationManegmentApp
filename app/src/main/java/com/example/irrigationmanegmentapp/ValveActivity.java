package com.example.irrigationmanegmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

import static com.example.irrigationmanegmentapp.Finals.RESULT_CODE_OK;
import static com.example.irrigationmanegmentapp.Finals.VALVE;

public class ValveActivity extends AppCompatActivity implements ValveAddFragment.addScheduleCallBack {


    private WaterValve mValve;
    private TextView VAL_TXT_valveName;
    private ImageView VAL_IMG_valveIcon;
    private SmoothBottomBar VAL_NAV_bottomBar;
    private ArrayList<Fragment> mFragmentsArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valve);

        //Get a specific valve from intent
        mValve = (WaterValve) getIntent().getSerializableExtra(VALVE);

        mFragmentsArrayList = createValveFragments();
        findViews();
        setValveLayout();

        VAL_NAV_bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelect(int i) {
                startValveFragment(i);
            }
        });
    }


    /**
     * inflate specific fragment from fragment's ArrayList
     * @param i
     */
    private void startValveFragment(int i) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, mFragmentsArrayList.get(i)).commit();
    }

    /**
     * Creates an ArrayList of 3 fragment to show inside the activity
     * @return ArrayList of fragments
     */
    private ArrayList<Fragment> createValveFragments() {
        ArrayList<Fragment> arr = new ArrayList<>();
        arr.add(new ValveChartsFragment(mValve.getweeklyAmounts(), getApplicationContext())); //Bar charts fragment
        arr.add(new ValveScheduleFragment(mValve.getIrrigationSchedule(), ValveActivity.this, dialogCallBack)); //Fragment to display all the watering plans
        arr.add(new ValveAddFragment()); // Fragment for adding new watering plan
        return arr;
    }

    /**
     * Find views
     */
    private void findViews() {
        VAL_TXT_valveName = findViewById(R.id.VAL_TXT_valveName);
        VAL_IMG_valveIcon = findViewById(R.id.VAL_IMG_valveIcon);
        VAL_NAV_bottomBar = findViewById(R.id.VAL_NAV_bottomBar);
    }

    /**
     * Sets the upper part of the activity
     * Sets the valve name and the icon
     */
    private void setValveLayout() {
        VAL_TXT_valveName.setText(mValve.getValveName());
        VAL_IMG_valveIcon.setImageResource(mValve.getIcon());
        startValveFragment(0);
    }

    /**
     * Returns the updated valve to the "ValvesFragment"
     */
    private void sendValveBackToProfileActivity(){
        Intent updatedValve = new Intent();
        updatedValve.putExtra(VALVE, mValve);
        setResult(RESULT_CODE_OK, updatedValve);
        finish();
    }

    /**
     * Add new irrigation plan to valve
     * @param plan
     */
    @Override
    public void addNewScheduleToValve(IrrigationPlan plan) {
        mValve.addNewmIrrigationSchedule(plan);
        sendValveBackToProfileActivity();
        Toast.makeText(this, "New Irrigation Plan Scheduled!", Toast.LENGTH_SHORT).show();

    }

    /**
     * Close the valve activity and sends the updated valve back to the profile activity
     */
    dialogCallBack dialogCallBack = new dialogCallBack() {
        @Override
        public void refresh() {
            sendValveBackToProfileActivity();
        }
    };
}
