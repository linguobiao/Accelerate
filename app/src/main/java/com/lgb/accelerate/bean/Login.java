package com.lgb.accelerate.bean;

import java.util.List;

/**
 * Created by linguobiao on 16/8/25.
 */
public class Login {
    private String result;
    private String session_id;
    private List<String> steps;
    private List<String> distances;
    private List<String> calories;
    private String user;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public List<String> getDistances() {
        return distances;
    }

    public void setDistances(List<String> distances) {
        this.distances = distances;
    }

    public List<String> getCalories() {
        return calories;
    }

    public void setCalories(List<String> calories) {
        this.calories = calories;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
