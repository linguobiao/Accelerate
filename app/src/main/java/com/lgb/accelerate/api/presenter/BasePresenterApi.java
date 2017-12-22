package com.lgb.accelerate.api.presenter;

import java.util.Map;

/**
 * Created by linguobiao on 16/8/25.
 */
public interface BasePresenterApi {

    void post(Map<String, Object> map);

    void stopLoad();

    void onDestroy();
}
