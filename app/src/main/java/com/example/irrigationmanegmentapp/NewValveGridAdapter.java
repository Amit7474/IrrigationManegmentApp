package com.example.irrigationmanegmentapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewValveGridAdapter extends BaseAdapter {

    Context mContext;
    //ArrayList of 4 Icons that the user can choose.
    ArrayList<Integer> ImageIDarrayList;

    public NewValveGridAdapter(Context context, ArrayList<Integer> arrayList) {

        this.mContext = context;
        this.ImageIDarrayList = arrayList;
    }

    @Override
    public int getCount() {
        return ImageIDarrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return ImageIDarrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.valve_grid_item, parent, false);
        }
        ImageView iv = convertView.findViewById(R.id.grid_item_image);
        TextView tx = convertView.findViewById(R.id.grid_item_label);
        tx.setVisibility(View.GONE);
        iv.setImageResource(ImageIDarrayList.get(position));

        return convertView;
    }
}
