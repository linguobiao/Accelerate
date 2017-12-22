package com.lgb.accelerate.imp.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.model.LoginModelApi;
import com.lgb.accelerate.api.presenter.LoginPresenterApi;
import com.lgb.accelerate.api.view.LoginActivityApi;
import com.lgb.accelerate.bean.Login;
import com.lgb.accelerate.bean.Profile;
import com.lgb.accelerate.bean.ResultLogin;
import com.lgb.accelerate.imp.model.LoginModel;
import com.lgb.accelerate.utils.SpUtils;
import com.lgb.accelerate.utils.StringUtils;
import com.lgb.accelerate.utils.http.GsonUtil;
import com.lgb.accelerate.utils.http.InternetUtil;
import com.lgb.accelerate.utils.http.ResultUtil;

/**
 * Created by linguobiao on 16/8/20.
 */
public class LoginPresenter implements LoginPresenterApi {

    private Context context;
    private LoginActivityApi view;
    private LoginModelApi model;

    public LoginPresenter(Context context, LoginActivityApi view) {
        this.context = context;
        this.view = view;
        model = new LoginModel();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLER_LOGIN_RESULT:
                    Log.i("test", "obj = " + msg.obj);

                    String resultStr = (String) msg.obj;
                    if (resultStr == null || resultStr.isEmpty()) {
                        view.showToast(context.getString(R.string.login_Failure));
                        break;
                    }
                    boolean isResultSuccess = ResultUtil.isResultSuccess(resultStr);
                    if (isResultSuccess) {
                        ResultUtil.parseLogin(resultStr);
                        SpUtils.getInstance().putString(Constant.KEY_PASSWORD, Global.password);
                        view.loginSuccess();
                    } else {
                        view.showToast(context.getString(R.string.login_Failure));
                        break;
                    }


//                    08-25 00:56:37.772 21256-17921/com.lgb.accelerate I/test: resutl =
//{"result":"y","session_id":"7E4EFA69E97FF6035592A375E5F3F33E","steps":[],"distances":[],"calories":[],
// "user":{"createDate":"2016-08-25 00:25:32","email":"11@qq.com","gender":1,"height":11,"id":3,"name":"lingb","stride":11,"weight":11}}

//                    if (login == null) {
//                        view.showToast(context.getString(R.string.login_Failure));
//                        break;
//                    }
//                    boolean result = login.isResult();
//                    if (result) {
//                        view.loginSuccess(login);
//                    } else {
//                        view.showToast(context.getString(R.string.login_Failure));
//                    }
//                    break;
            }
        }
    };

    @Override
    public void login(String account, String password) {
        if (!InternetUtil.isConnectingToInternet(context)) {
            view.showToast(context.getString(R.string.public_No_network));
            return;
        }
        if (StringUtils.isNull(account)) {
            view.showToast(context.getString(R.string.login_E_c_b_e));
            return;
        }
        if (!StringUtils.isEmail(account)) {
            view.showToast(context.getString(R.string.login_P_e_a_v_e_a));
            return;
        }
        if (StringUtils.isNull(password)) {
            view.showToast(context.getString(R.string.login_P_c_b_e));
            return;
        }
        Global.password = password;
        view.showDialog();
        model.login(account, password, handler);
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
