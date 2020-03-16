package com.example.irrigationmanegmentapp;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.irrigationmanegmentapp.Finals.DAYS_OF_THE_WEEK;
import static com.example.irrigationmanegmentapp.Finals.LITERS_PER_DAY;

public class BarChartsManager {

    private Context mContext;
    private BarChart mBarChart;
    private HashMap<String, Long> weeklyAmounts;

    public BarChartsManager(Context context, HashMap<String, Long> weeklyAmounts) {
        this.mContext = context;
        this.weeklyAmounts = weeklyAmounts;
        createBarCharts();
    }

    /**
     * Creates Bar charts and configures it
     */
    private void createBarCharts() {
        mBarChart = new BarChart(mContext);
        BarDataSet set = new BarDataSet(generateBarEntries(), LITERS_PER_DAY);
        BarData data = new BarData(set);
        data.setBarWidth(0.3f);
        addDaysToChart(mBarChart);
        mBarChart.setFitBars(true); // make the x-axis fit exactly all bars
        set.setColors(ContextCompat.getColor(mContext, R.color.GreenSuccess));
        mBarChart.setData(data);
        mBarChart.setDrawGridBackground(false);
        mBarChart.setDrawBarShadow(false);
        mBarChart.animateY(1000);
        mBarChart.invalidate(); // refresh

    }

    /**
     * Converts the HashMap of the daily usage of water per day to ArrayList that can be shown on the chart
     * @return ArrayList of daily usage
     */
    private List<BarEntry> generateBarEntries() {
        List<BarEntry> entries = new ArrayList<>();
        for(int i=0;i<DAYS_OF_THE_WEEK.length; i++){
            entries.add(new BarEntry((float) i, weeklyAmounts.get(DAYS_OF_THE_WEEK[i])));
        }
        return entries;
    }

    /**
     * Fill the X-axis of the chart with days
     * @param mBarChart
     */
    private void addDaysToChart(BarChart mBarChart) {
        ArrayList days = new ArrayList();
        for(String day: DAYS_OF_THE_WEEK){
            days.add(day);
        }
        XAxis xAxis = mBarChart.getXAxis();
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(days);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
    }

    /**\
     * Simple getter
     * @return Bar Chart
     */
    public BarChart getmBarChart() {
        return this.mBarChart;
    }


}
