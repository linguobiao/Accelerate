package com.lgb.accelerate.api.model;

import android.os.Handler;

import com.lgb.accelerate.bean.User;

/**
 * Created by linguobiao on 16/8/20.
 */
public interface SignModelApi {

    void signUp(User user, Handler handler);

    void stopLoad();
}
