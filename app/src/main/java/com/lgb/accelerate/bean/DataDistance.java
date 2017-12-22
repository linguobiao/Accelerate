package com.lgb.accelerate.bean;

import com.lgb.accelerate.api.other.Data;

/**
 * Created by linguobiao on 16/8/26.
 */
public class DataDistance implements Data{

    private String date;
    private double total_distances;
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
        total_distances = o;

    }

    @Override
    public double getValue() {
        return total_distances;
    }

    @Override
    public String toString() {
        return "DataDistance{" +
                "date='" + date + '\'' +
                ", total_distances=" + total_distances +
                '}';
    }
}
