package com.lgb.accelerate.imp.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.model.BaseModelApi;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.model.AddTracksModel;
import com.lgb.accelerate.imp.model.ForgetModel;
import com.lgb.accelerate.utils.CheckUtils;
import com.lgb.accelerate.utils.StringUtils;
import com.lgb.accelerate.utils.http.InternetUtil;
import com.lgb.accelerate.utils.http.ResultUtil;

import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class ForgetPresenter implements BasePresenterApi {

    private Context context;
    private BaseViewApi view;
    private BaseModelApi model;

    public ForgetPresenter(Context context, BaseViewApi view) {
        this.context = context;
        this.view = view;
        model = new ForgetModel();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLER_SEND_EMAIL_RESULT:
                    String resultStr = (String) msg.obj;
                    boolean isResultSuccess = ResultUtil.isResultSuccess(resultStr);
                    // 成功
                    if (isResultSuccess) {
                        view.showToast(context.getString(R.string.login_P_c_y_e));
                        view.postSuccess(null);
                    }
                    // 失败
                    else {
                        ResultFail fail = ResultUtil.parseFailResult(resultStr);
                        view.postFail(fail);
                        view.showToast(context.getString(R.string.login_Request_denied));
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

        // email为null
        if (StringUtils.isNull((String)map.get(Constant.KEY_EMAIL))) {
            view.showToast(context.getString(R.string.login_E_c_b_e));
            return;
        }
        // email不正常
        if (!StringUtils.isEmail((String)map.get(Constant.KEY_EMAIL))) {
            view.showToast(context.getString(R.string.login_P_e_a_v_e_a));
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
