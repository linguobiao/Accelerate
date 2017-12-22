package com.lgb.accelerate.imp.model;

import android.os.Handler;
import android.util.Log;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.api.model.LoginModelApi;
import com.lgb.accelerate.task.LoginTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class LoginModel implements LoginModelApi {

    private LoginTask task;
    @Override
    public void login(String account, String password, Handler handler) {
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.KEY_EMAIL, account);
        map.put(Constant.KEY_PASSWORD, password);
        map.put(Constant.KEY_HANDLER, handler);
        task = new LoginTask();
        task.execute(map);
    }

    @Override
    public void stopLoad() {
        if (task != null) {
            task.cancel(true);
        }
    }
}
