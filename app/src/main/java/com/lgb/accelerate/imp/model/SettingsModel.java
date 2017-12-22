package com.lgb.accelerate.imp.model;

import android.os.Handler;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.api.model.BaseModelApi;
import com.lgb.accelerate.task.AddTracksTask;
import com.lgb.accelerate.task.ResetUserTask;

import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class SettingsModel implements BaseModelApi {

    private ResetUserTask task;
    @Override
    public void post(Map<String, Object> map, Handler handler) {

        map.put(Constant.KEY_HANDLER, handler);
        task = new ResetUserTask();
        task.execute(map);
    }

    @Override
    public void stopLoad() {
        if (task != null) {
            task.cancel(true);
        }
    }
}
