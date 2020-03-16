package com.example.irrigationmanegmentapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomGridAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<WaterValve> mWaterValveList;


    public CustomGridAdapter(Context context, ArrayList<WaterValve> waterValves) {
        this.mContext = context;
        this.mWaterValveList = waterValves;
    }

    @Override
    public int getCount() {
        return mWaterValveList.size();
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
            // get layout from valve_grid_item.xml
            convertView = inflater.inflate(R.layout.valve_grid_item, null);

            // set water valve name into textview
            TextView textView = convertView.findViewById(R.id.grid_item_label);
            textView.setText(mWaterValveList.get(position).getValveName());

            // set image based on selected water valve
            ImageView imageView = convertView.findViewById(R.id.grid_item_image);
            imageView.setImageResource(mWaterValveList.get(position).getIcon());
        }

        return convertView;
    }
}
