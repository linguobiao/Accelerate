package com.lgb.accelerate.bean;

import java.util.Calendar;

/**
 * Created by linguobiao on 16/8/18.
 */
public class HistoryDay {

    private Calendar date;
    private int steps;
    private double distances;
    private double calories;

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public double getDistances() {
        return distances;
    }

    public void setDistances(double distances) {
        this.distances = distances;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

}
