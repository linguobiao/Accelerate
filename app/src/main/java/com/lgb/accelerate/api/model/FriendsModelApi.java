package com.lgb.accelerate.api.model;

import android.os.Handler;

import java.util.Map;

/**
 * Created by linguobiao on 16/8/25.
 */
public interface FriendsModelApi {

    void postData(Map<String, Object> map, Handler handler);
    void postFriends(Map<String, Object> map, Handler handler);

    void stopLoad();
}
