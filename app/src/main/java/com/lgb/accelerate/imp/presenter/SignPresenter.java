package com.lgb.accelerate.imp.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.model.BaseModelApi;
import com.lgb.accelerate.api.model.SignModelApi;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.presenter.SignPresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.api.view.SignActivityApi;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.bean.ResultSignUp;
import com.lgb.accelerate.bean.User;
import com.lgb.accelerate.imp.model.SignModel;
import com.lgb.accelerate.utils.StringUtils;
import com.lgb.accelerate.utils.http.GsonUtil;
import com.lgb.accelerate.utils.http.InternetUtil;
import com.lgb.accelerate.utils.http.ResultUtil;

import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class SignPresenter implements BasePresenterApi {

    private Context context;
    private BaseViewApi view;
    private BaseModelApi model;

    public SignPresenter(Context context, BaseViewApi view) {
        this.context = context;
        this.view = view;
        model = new SignModel();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLER_SIGN_UP_RESULT:
                    String resultStr = (String) msg.obj;
                    boolean isResultSuccess = ResultUtil.isResultSuccess(resultStr);
                    // 成功
                    if (isResultSuccess) {
                        view.postSuccess(null);
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
        // 名字不正常
        if (StringUtils.isNull((String)map.get(Constant.KEY_NAME))) {
            view.showToast(context.getString(R.string.sign_N_c_b_e));
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
        // 密码为null
        if (StringUtils.isNull((String)map.get(Constant.KEY_PASSWORD))) {
            view.showToast(context.getString(R.string.login_P_c_b_e));
            return;
        }
        // 密码不一致
        if (!StringUtils.isEquals((String)map.get(Constant.KEY_PASSWORD), (String)map.get(Constant.KEY_PASSWORD_CONFIRM))) {
            view.showToast(context.getString(R.string.sign_P_d_n_m));
            return;
        }
        // 对步距进行转换
        String stride = StringUtils.parseStride((String)map.get(Constant.KEY_STRIDE));
        // 步距不正常
        if (stride.equalsIgnoreCase(Constant.FALSE)) {
            view.showToast(context.getString(R.string.sign_S_c_b_z));
            return;
        }
        // 步距正常
        else {
            map.put(Constant.KEY_STRIDE, stride);
        }
        // 对身高进行转换
        String height = StringUtils.parseHeight((String)map.get(Constant.KEY_HEIGHT_FT), (String)map.get(Constant.KEY_HEIGHT_IN), (String)map.get(Constant.KEY_HEIGHT_CM));
        // 身高不正常
        if (height.equalsIgnoreCase(Constant.FALSE)) {
            view.showToast(context.getString(R.string.sign_P_e_f));
            return;
        }
        // 身高正常
        else {
            map.put(Constant.KEY_HEIGHT, height);
        }
        // 对体重进行转换
        String weight = StringUtils.parseWeight((String)map.get(Constant.KEY_WEIGHT));
        // 体重不正常
        if (weight.equalsIgnoreCase(Constant.FALSE)) {
            view.showToast(context.getString(R.string.sign_W_c_b_z));
            return;
        }
        // 体重正常
        else {
            map.put(Constant.KEY_WEIGHT, weight);
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
