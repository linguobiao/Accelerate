package com.lgb.accelerate.api.view;

import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.bean.ResultLogin;

import java.util.Map;

/**
 * Created by linguobiao on 16/8/25.
 */
public interface BaseViewApi {
    void showToast(String msg);
    void postSuccess(Object object);
    void postFail(ResultFail fail);
    void showDialog();
}
