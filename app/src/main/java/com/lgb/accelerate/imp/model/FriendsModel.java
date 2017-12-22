package com.lgb.accelerate.imp.model;

import android.os.Handler;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.api.model.BaseModelApi;
import com.lgb.accelerate.api.model.FriendsModelApi;
import com.lgb.accelerate.task.AddTracksTask;
import com.lgb.accelerate.task.GetDataTask;
import com.lgb.accelerate.task.GetFriendDataTask;

import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class FriendsModel implements FriendsModelApi {

    private GetFriendDataTask task;
    private GetDataTask taskData;

    @Override
    public void postData(Map<String, Object> map, Handler handler) {
        map.put(Constant.KEY_HANDLER, handler);
        taskData = new GetDataTask();
        taskData.execute(map);
    }

    @Override
    public void postFriends(Map<String, Object> map, Handler handler) {

        map.put(Constant.KEY_HANDLER, handler);
        task = new GetFriendDataTask();
        task.execute(map);
    }

    @Override
    public void stopLoad() {
        if (task != null) {
            task.cancel(true);
        }
        if (taskData != null) {
            taskData.cancel(true);
        }
    }
}
