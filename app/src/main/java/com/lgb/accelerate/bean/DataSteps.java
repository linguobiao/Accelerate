package com.lgb.accelerate.bean;

import com.lgb.accelerate.api.other.Data;

/**
 * Created by linguobiao on 16/8/26.
 */
public class DataSteps implements Data{

    private String date;
    private double total_steps;
    private int week;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void setWeek(int week) {
        this.week = week;
    }

    @Override
    public int getWeek() {
        return week;
    }


    @Override
    public void setValue(double o) {
        total_steps = o;

    }

    @Override
    public double getValue() {
        return total_steps;
    }

    @Override
    public String toString() {
        return "DataSteps{" +
                "date='" + date + '\'' +
                ", total_steps=" + total_steps +
                '}';
    }
}
