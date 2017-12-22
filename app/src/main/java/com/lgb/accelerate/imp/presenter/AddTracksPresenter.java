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
import com.lgb.accelerate.imp.model.TracksModel;
import com.lgb.accelerate.utils.CheckUtils;
import com.lgb.accelerate.utils.StringUtils;
import com.lgb.accelerate.utils.http.InternetUtil;
import com.lgb.accelerate.utils.http.ResultUtil;

import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class AddTracksPresenter implements BasePresenterApi {

    private Context context;
    private BaseViewApi view;
    private BaseModelApi model;

    public AddTracksPresenter(Context context, BaseViewApi view) {
        this.context = context;
        this.view = view;
        model = new AddTracksModel();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLER_ADD_TRACK_RESULT:
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

        // 步数不正常
        if (!CheckUtils.isStepsOk((String)map.get(Constant.KEY_STEPS))) {
            view.showToast(context.getString(R.string.tracks_Not_valid_steps));
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
