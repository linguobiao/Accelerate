package com.lgb.accelerate.imp.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.model.BaseModelApi;
import com.lgb.accelerate.api.model.LoginModelApi;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.presenter.LoginPresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.api.view.LoginActivityApi;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.bean.ResultLogin;
import com.lgb.accelerate.imp.model.LoginModel;
import com.lgb.accelerate.imp.model.TracksModel;
import com.lgb.accelerate.utils.StringUtils;
import com.lgb.accelerate.utils.http.GsonUtil;
import com.lgb.accelerate.utils.http.InternetUtil;
import com.lgb.accelerate.utils.http.ResultUtil;

import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class TracksPresenter implements BasePresenterApi {

    private Context context;
    private BaseViewApi view;
    private BaseModelApi model;

    public TracksPresenter(Context context, BaseViewApi view) {
        this.context = context;
        this.view = view;
        model = new TracksModel();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLER_GET_TRACK_RESULT:
                    String resultStr = (String) msg.obj;
                    boolean isResultSuccess = ResultUtil.isResultSuccess(resultStr);
                    // 成功
                    if (isResultSuccess) {
                        Map<String, String> map = ResultUtil.parseGetTrack(resultStr);
                        view.postSuccess(map);
                    }
                    // 失败
                    else {
                        ResultFail fail = ResultUtil.parseFailResult(resultStr);
                        view.postFail(fail);
                        view.showToast(context.getString(R.string.public_Failure));
                    }
                    break;
            }
        }
    };

    @Override
    public void post(Map<String, Object> map) {
        if (!InternetUtil.isConnectingToInternet(context)) {
            view.showToast(context.getString(R.string.public_No_network));
            return;
        }
        view.showDialog();
        model.post(map, handler);
    }

    @Override
    public void stopLoad() {
        model.stopLoad();
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
