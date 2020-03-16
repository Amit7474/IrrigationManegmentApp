package com.example.irrigationmanegmentapp;

import java.io.Serializable;

public class IrrigationPlan implements Serializable {

    private int mDay;
    private int mStartHour;
    private int mStartMinute;
    private int mEndHour;
    private int mEndMinute;

    public IrrigationPlan() {
    }

    /**
     * Single irrigation plan that defined the interval of watering schedule.
     * @param mDay
     * @param mStartHour
     * @param mStartMinute
     * @param mEndHour
     * @param mEndMinute
     */
    public IrrigationPlan(int mDay, int mStartHour, int mStartMinute, int mEndHour, int mEndMinute) {
        this.mDay = mDay;
        this.mStartHour = mStartHour;
        this.mStartMinute = mStartMinute;
        this.mEndHour = mEndHour;
        this.mEndMinute = mEndMinute;
    }

    public int getmDay() {
        return mDay;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public int getmStartHour() {
        return mStartHour;
    }

    public void setmStartHour(int mStartHour) {
        this.mStartHour = mStartHour;
    }

    public int getmStartMinute() {
        return mStartMinute;
    }

    public void setmStartMinute(int mStartMinute) {
        this.mStartMinute = mStartMinute;
    }

    public int getmEndHour() {
        return mEndHour;
    }

    public void setmEndHour(int mEndHour) {
        this.mEndHour = mEndHour;
    }

    public int getmEndMinute() {
        return mEndMinute;
    }

    public void setmEndMinute(int mEndMinute) {
        this.mEndMinute = mEndMinute;
    }

    @Override
    public String toString() {
        return "IrrigationPlan{" +
                "mDay=" + mDay +
                ", mStartHour=" + mStartHour +
                ", mStartMinute=" + mStartMinute +
                ", mEndHour=" + mEndHour +
                ", mEndMinute=" + mEndMinute +
                '}';
    }
}
