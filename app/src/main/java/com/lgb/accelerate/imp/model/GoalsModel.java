package com.lgb.accelerate.imp.model;

import android.os.Handler;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.api.model.BaseModelApi;
import com.lgb.accelerate.api.model.GoalsModelApi;
import com.lgb.accelerate.task.GetDailyGoalTask;
import com.lgb.accelerate.task.GetDataTask;
import com.lgb.accelerate.task.GetGoalBoolTask;
import com.lgb.accelerate.task.GetTracksTask;

import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class GoalsModel implements GoalsModelApi {

    private GetDataTask taskData;
    private GetDailyGoalTask taskGoal;
    private GetGoalBoolTask taskGoalBool;

    @Override
    public void postData(Map<String, Object> map, Handler handler) {
        map.put(Constant.KEY_HANDLER, handler);
        taskData = new GetDataTask();
        taskData.execute(map);
    }

    @Override
    public void postGoal(Map<String, Object> map, Handler handler) {
        map.put(Constant.KEY_HANDLER, handler);
        taskGoal = new GetDailyGoalTask();
        taskGoal.execute(map);
    }

    @Override
    public void postGoalWeekly(Map<String, Object> map, Handler handler) {
        map.put(Constant.KEY_HANDLER, handler);
        taskGoalBool = new GetGoalBoolTask();
        taskGoalBool.execute(map);
    }

    @Override
    public void stopLoad() {
        if (taskData != null) {
            taskData.cancel(true);
        }
        if (taskGoal != null) {
            taskGoal.cancel(true);
        }
        if (taskGoalBool != null) {
            taskGoalBool.cancel(true);
        }
    }
}
