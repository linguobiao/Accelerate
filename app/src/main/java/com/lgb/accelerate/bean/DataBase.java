package com.lgb.accelerate.bean;

/**
 * Created by linguobiao on 16/8/26.
 */
public class DataBase {

    private String steps;
    private String distances;
    private String calories;

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getDistances() {
        return distances;
    }

    public void setDistances(String distances) {
        this.distances = distances;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "DataBase{" +
                "steps='" + steps + '\'' +
                ", distances='" + distances + '\'' +
                ", calories='" + calories + '\'' +
                '}';
    }
}
