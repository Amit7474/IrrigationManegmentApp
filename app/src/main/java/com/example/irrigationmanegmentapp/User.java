package com.example.irrigationmanegmentapp;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String mobileNumber;
    private String firstName;
    private ArrayList<WaterValve> waterValveArrayList;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String mobileNumber, String firstName) {
        this.mobileNumber = mobileNumber;
        this.firstName = firstName;
        this.waterValveArrayList = new ArrayList<>();
        this.waterValveArrayList.add(new WaterValve("Tap To Add", R.drawable.ic_add));
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getFirstName() {
        return firstName;
    }


    public void updateValve(WaterValve valve, int index) {
        this.waterValveArrayList.set(index, valve);
    }

    public ArrayList<WaterValve> getWaterValveArrayList() {
        return this.waterValveArrayList;
    }

    public void addNewValveToArrayList(WaterValve valve) {
        this.waterValveArrayList.add(0, valve);
    }

    public void deleteValveFromArrayList(int position) {
        this.waterValveArrayList.remove(position);
    }


    @Override
    public String toString() {
        return "User{" +
                "mobileNumber='" + mobileNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", waterValveArrayList=" + waterValveArrayList +
                '}';
    }
}
