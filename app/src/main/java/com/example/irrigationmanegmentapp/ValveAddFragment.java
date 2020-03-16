package com.example.irrigationmanegmentapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.michaldrabik.classicmaterialtimepicker.CmtpTimeDialogFragment;
import com.michaldrabik.classicmaterialtimepicker.OnTime24PickedListener;
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime24;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static com.example.irrigationmanegmentapp.Finals.DAYS_OF_THE_WEEK;

public class ValveAddFragment extends Fragment {

    public interface addScheduleCallBack {
        void addNewScheduleToValve(IrrigationPlan plan);
    }


    private Button ADFR_BTN_start, ADFR_BTN_end, ADDFR_BTN_add;
    private CmtpTimeDialogFragment ADFR_PCK_start, ADFR_PCK_end;
    private int startHour, startMinute, endHour, endMinute;
    private NiceSpinner ADFR_SP_days;
    private List<String> days;
    private addScheduleCallBack mAddScheduleCallBack;


    public ValveAddFragment() {
        this.days = new ArrayList<>();
        fillDays();
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
            mAddScheduleCallBack = (addScheduleCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement addScheduleCallBack");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        ADFR_BTN_start = view.findViewById(R.id.ADFR_BTN_start);
        ADFR_BTN_end = view.findViewById(R.id.ADFR_BTN_end);
        ADDFR_BTN_add = view.findViewById(R.id.ADDFR_BTN_add);
        ADFR_SP_days = view.findViewById(R.id.ADFR_SP_days);
        ADFR_BTN_start.setOnClickListener(pickTimeListener);
        ADFR_BTN_end.setOnClickListener(pickTimeListener);
        ADDFR_BTN_add.setOnClickListener(addNewListener);

        //Bind between the spinner to the days ArrayList
        ADFR_SP_days.attachDataSource(days);
//        ADFR_SP_days.setOnSpinnerItemSelectedListener(spinnerListener);

        return view;
    }

//    OnSpinnerItemSelectedListener spinnerListener = new OnSpinnerItemSelectedListener() {
//        @Override
//        public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
//        }
//    };

    /**
     * Handles and decides witch time picker was pressed
     */
    View.OnClickListener pickTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ADFR_BTN_start:
                    pickTime(ADFR_PCK_start, ADFR_BTN_start);
                    break;

                case R.id.ADFR_BTN_end:
                    pickTime(ADFR_PCK_end, ADFR_BTN_end);
                    break;
            }
        }
    };

    /**
     * Adding new irrigation plan
     */
    View.OnClickListener addNewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mAddScheduleCallBack.addNewScheduleToValve(new IrrigationPlan(ADFR_SP_days.getSelectedIndex(), startHour, startMinute, endHour, endMinute));

        }
    };

    /** Gets the time that was picked and displays it on the buttons of the time pickers
     * and saves the selected hours and minutes
     * @param mTimePicker
     * @param btn
     */
    private void pickTime(CmtpTimeDialogFragment mTimePicker, final Button btn) {
        mTimePicker = CmtpTimeDialogFragment.newInstance();
        mTimePicker.setInitialTime24(12, 0);
        mTimePicker.show(getFragmentManager(), "Tag");
        mTimePicker.setOnTime24PickedListener(new OnTime24PickedListener() {
            @Override
            public void onTimePicked(CmtpTime24 cmtpTime24) {
                btn.setText(TimeFormatter(cmtpTime24.getHour(), cmtpTime24.getMinute()));
                if (btn.getId() == R.id.ADFR_BTN_start) {
                    startHour = cmtpTime24.getHour();
                    startMinute = cmtpTime24.getMinute();
                } else {
                    endHour = cmtpTime24.getHour();
                    endMinute = cmtpTime24.getMinute();
                }
            }
        });
    }

    /**
     * Creates ArrayList of days
     */
    private void fillDays() {
        for (String day : DAYS_OF_THE_WEEK) {
            days.add(day);
        }
    }

    /**
     * Takes the hours and the minutes separately and turns them into readable time.
     * example: it gets the numbers 12 and 3 the makes them --> 12:03 instead of 12:3
     * @param hour
     * @param minute
     * @return readable Time "-- : --"
     */
    private String TimeFormatter(int hour, int minute) {
        String formattedHour = "";
        String formattedMinute = "";
        if (hour > 9) {
            formattedHour = hour + "";
        } else {
            formattedHour = "0" + hour;
        }
        if (minute > 9) {
            formattedMinute = minute + "";
        } else {
            formattedMinute = "0" + minute;
        }
        return formattedHour + ":" + formattedMinute;
    }
}
