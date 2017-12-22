package com.lgb.accelerate.api.presenter;

import com.lgb.accelerate.bean.User;

/**
 * Created by linguobiao on 16/8/20.
 */
public interface SignPresenterApi {

    void signUp(User user);

    void stopLoad();

    void onDestroy();
}
