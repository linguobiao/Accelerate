package com.lgb.accelerate.api.model;

import android.os.Handler;

/**
 * Created by linguobiao on 16/8/20.
 */
public interface LoginModelApi {

    void login(String account, String password, Handler handler);

    void stopLoad();
}
