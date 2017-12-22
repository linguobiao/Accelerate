package com.lgb.accelerate.api.model;

import android.os.Handler;

import java.util.Map;

/**
 * Created by linguobiao on 16/8/25.
 */
public interface GoalsModelApi {

    void postData(Map<String, Object> map, Handler handler);
    void postGoal(Map<String, Object> map, Handler handler);
    void postGoalWeekly(Map<String, Object> map, Handler handler);

    void stopLoad();
}
