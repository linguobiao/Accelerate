package com.lgb.accelerate.imp.model;

import android.os.Handler;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.api.model.BaseModelApi;
import com.lgb.accelerate.api.model.SignModelApi;
import com.lgb.accelerate.bean.User;
import com.lgb.accelerate.task.SignUpTask;
import com.lgb.accelerate.task.UpdateUserTask;
import com.lgb.accelerate.utils.SpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class UpdateUserModel implements BaseModelApi {

    private UpdateUserTask task;
    @Override
    public void post(Map<String, Object> map, Handler handler) {

        map.put(Constant.KEY_HANDLER, handler);
        task = new UpdateUserTask();
        task.execute(map);
    }

    @Override
    public void stopLoad() {
        if (task != null) {
            task.cancel(true);
        }
    }
}
