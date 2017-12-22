package com.lgb.accelerate.api.presenter;

/**
 * Created by linguobiao on 16/8/20.
 */
public interface LoginPresenterApi {

    void login(String account, String password);

    void stopLoad();

    void onDestroy();
}
