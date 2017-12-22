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
import com.lgb.accelerate.imp.model.DailyGoalModel;
import com.lgb.accelerate.utils.CheckUtils;
import com.lgb.accelerate.utils.http.InternetUtil;
import com.lgb.accelerate.utils.http.ResultUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class DailyGoalPresenter implements BasePresenterApi {

    private Context context;
    private BaseViewApi view;
    private BaseModelApi model;

    public DailyGoalPresenter(Context context, BaseViewApi view) {
        this.context = context;
        this.view = view;
        model = new DailyGoalModel();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // 增加目标步数结果返回
                case Constant.HANDLER_ADD_DAILY_GOAL_RESULT:
                    String resultStr = (String) msg.obj;
                    boolean isResultSuccess = ResultUtil.isResultSuccess(resultStr);
                    // 成功
                    if (isResultSuccess) {
                        Map<String, Object> map = new HashMap<>();
                        map.put(Constant.KEY_TYPE_POST, Constant.TYPE_ADD);
                        view.postSuccess(map);
                    }
                    // 失败
                    else {
                        ResultFail fail = ResultUtil.parseFailResult(resultStr);
                        view.postFail(fail);
                        view.showToast(context.getString(R.string.public_Failure));
                    }
                    break;
                // 获取目标步数结果返回
                case Constant.HANDLER_GET_DAILY_GOAL_RESULT:
                    String resultStrGet = (String) msg.obj;
                    boolean isResultSuccessGet = ResultUtil.isResultSuccess(resultStrGet);
                    // 成功
                    if (isResultSuccessGet) {
                        Map<String, Object> map = new HashMap<>();
                        map.put(Constant.KEY_TYPE_POST, Constant.TYPE_GET);
                        String goalSteps = ResultUtil.parseGetDailyGoal(resultStrGet);
                        map.put(Constant.KEY_STEPS, goalSteps);
                        view.postSuccess(map);
                    }
                    // 失败
                    else {
                        // 获取失败，可能没有上传过目标，将目标设置为0返回去
                        Map<String, Object> map = new HashMap<>();
                        map.put(Constant.KEY_TYPE_POST, Constant.TYPE_GET);
                        map.put(Constant.KEY_STEPS, "0");
                        view.postSuccess(map);
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

        int type = (int) map.get(Constant.KEY_TYPE_POST);
        if (type == Constant.TYPE_ADD) {
            // 步数不正常
            if (!CheckUtils.isStepsOk((String)map.get(Constant.KEY_STEPS))) {
                view.showToast(context.getString(R.string.tracks_Not_valid_steps));
                return;
            }

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
