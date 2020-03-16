package com.example.irrigationmanegmentapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import static com.example.irrigationmanegmentapp.Finals.DAYS_OF_THE_WEEK;

public class SchedulesGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<IrrigationPlan> mIrrigationPlans;


    public SchedulesGridAdapter(Context context, List<IrrigationPlan> mPlans) {
        this.mContext = context;
        this.mIrrigationPlans = mPlans;
    }

    @Override
    public int getCount() {
        return mIrrigationPlans.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            //Get the day, start time and end time of a specific irrigation plan
            String day = DAYS_OF_THE_WEEK[mIrrigationPlans.get(position).getmDay()];
            String start = TimeFormatter(mIrrigationPlans.get(position).getmStartHour(), mIrrigationPlans.get(position).getmStartMinute());
            String end = TimeFormatter(mIrrigationPlans.get(position).getmEndHour(), mIrrigationPlans.get(position).getmEndMinute());

            convertView = inflater.inflate(R.layout.plan_grid_item, null);

            //Display the irrigation plan details into textviews
            TextView dayTextView = convertView.findViewById(R.id.plan_grid_item_day);
            dayTextView.setText("Day: " + day);
            TextView startTextView = convertView.findViewById(R.id.plan_grid_item_start);
            startTextView.setText("Start: " + start);
            TextView endTextView = convertView.findViewById(R.id.plan_grid_item_end);
            endTextView.setText("End: " + end);

        }

        return convertView;
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
