package com.lgb.accelerate.imp.model;

import android.os.Handler;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.api.model.BaseModelApi;
import com.lgb.accelerate.task.AddDailyGoalTask;
import com.lgb.accelerate.task.AddTracksTask;
import com.lgb.accelerate.task.GetDailyGoalTask;

import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class DailyGoalModel implements BaseModelApi {

    private AddDailyGoalTask task;
    private GetDailyGoalTask taskGet;

    @Override
    public void post(Map<String, Object> map, Handler handler) {

        map.put(Constant.KEY_HANDLER, handler);

        int state = (int) map.get(Constant.KEY_TYPE_POST);
        if (state == Constant.TYPE_ADD) {
            task = new AddDailyGoalTask();
            task.execute(map);

        } else if (state == Constant.TYPE_GET) {
            taskGet = new GetDailyGoalTask();
            taskGet.execute(map);
        }
    }

    @Override
    public void stopLoad() {
        if (task != null) {
            task.cancel(true);
        }
        if (taskGet != null) {
            taskGet.cancel(true);
        }
    }
}
