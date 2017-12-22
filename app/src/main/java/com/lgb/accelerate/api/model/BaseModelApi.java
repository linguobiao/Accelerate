package com.lgb.accelerate.api.model;

import android.os.Handler;

import com.lgb.accelerate.bean.User;

import java.util.Map;

/**
 * Created by linguobiao on 16/8/25.
 */
public interface BaseModelApi {

    void post(Map<String, Object> map, Handler handler);

    void stopLoad();
}
