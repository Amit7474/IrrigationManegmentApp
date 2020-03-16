package com.example.irrigationmanegmentapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.irrigationmanegmentapp.Finals.DAYS_OF_THE_WEEK;

public class WaterValve implements Serializable {

    private String valveName;
    private int icon;
    private List<IrrigationPlan> IrrigationSchedule;
    private HashMap<String, Long> weeklyAmounts;

    public WaterValve() {
    }

    public WaterValve(String name, int drawableRes) {
        this.valveName = name;
        this.icon = drawableRes;
        this.weeklyAmounts = new HashMap<>();
        this.IrrigationSchedule = new ArrayList<>();
        fillHashmap();
    }

    public String getValveName() {
        return valveName;
    }

    public int getIcon() {
        return icon;
    }

    public void addNewmIrrigationSchedule(IrrigationPlan plan) {
        if (this.IrrigationSchedule == null)
            this.IrrigationSchedule = new ArrayList<>();
        this.IrrigationSchedule.add(plan);
    }

    public List<IrrigationPlan> getIrrigationSchedule() {
        return this.IrrigationSchedule;
    }


    public HashMap<String, Long> getweeklyAmounts() {
        return this.weeklyAmounts;
    }

    private void fillHashmap() {
        for(int i=0;i<DAYS_OF_THE_WEEK.length;i++){
            this.weeklyAmounts.put(DAYS_OF_THE_WEEK[i], (long)0);
        }
    }

    @Override
    public String toString() {
        return "WaterValve{" +
                "valveName='" + valveName + '\'' +
                ", icon=" + icon +
                '}';
    }
}
