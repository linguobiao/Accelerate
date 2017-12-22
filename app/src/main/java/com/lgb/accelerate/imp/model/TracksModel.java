package com.lgb.accelerate.imp.model;

import android.os.Handler;
import android.os.Message;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.model.BaseModelApi;
import com.lgb.accelerate.api.model.LoginModelApi;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.bean.ResultLogin;
import com.lgb.accelerate.task.GetTracksTask;
import com.lgb.accelerate.task.LoginTask;
import com.lgb.accelerate.utils.http.GsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class TracksModel implements BaseModelApi {

    private GetTracksTask task;
    @Override
    public void post(Map<String, Object> map, Handler handler) {

        map.put(Constant.KEY_HANDLER, handler);
        task = new GetTracksTask();
        task.execute(map);
    }

    @Override
    public void stopLoad() {
        if (task != null) {
            task.cancel(true);
        }
    }
}
