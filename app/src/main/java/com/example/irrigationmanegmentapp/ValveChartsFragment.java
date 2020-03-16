package com.example.irrigationmanegmentapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ValveChartsFragment extends Fragment {

    private Context mContext;
    private FrameLayout CHRT_FRG_Charts;
    private HashMap<String, Long> weeklyAmounts;

    public ValveChartsFragment() {
    }

    public ValveChartsFragment(HashMap<String, Long> weeklyAmounts, Context context) {
        this.mContext = context;
        this.weeklyAmounts = weeklyAmounts;
    }

    /**
     * Creates Bar chart manager that handles the wekly usage and how to display it on the bar charts
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        CHRT_FRG_Charts = view.findViewById(R.id.CHRT_FRG_Charts);
        BarChartsManager mBarChartsManager = new BarChartsManager(mContext, weeklyAmounts);
        CHRT_FRG_Charts.addView(mBarChartsManager.getmBarChart());
        return view;
    }
}
