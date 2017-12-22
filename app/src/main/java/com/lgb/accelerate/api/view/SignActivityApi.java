package com.lgb.accelerate.api.view;

import com.lgb.accelerate.bean.ResultLogin;
import com.lgb.accelerate.bean.ResultSignUp;

/**
 * Created by linguobiao on 16/8/20.
 */
public interface SignActivityApi {
    void showToast(String msg);
    void signUpSuccess(ResultSignUp signUp);
    void showDialog();
}
