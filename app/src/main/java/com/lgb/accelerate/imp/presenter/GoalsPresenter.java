package com.lgb.accelerate.imp.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.model.BaseModelApi;
import com.lgb.accelerate.api.model.GoalsModelApi;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.model.GoalsModel;
import com.lgb.accelerate.imp.model.TracksModel;
import com.lgb.accelerate.utils.CalendarHelper;
import com.lgb.accelerate.utils.FormatHelper;
import com.lgb.accelerate.utils.SpUtils;
import com.lgb.accelerate.utils.http.InternetUtil;
import com.lgb.accelerate.utils.http.ResultUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class GoalsPresenter implements BasePresenterApi {

    private Context context;
    private BaseViewApi view;
    private GoalsModelApi model;

    Map<String, Boolean> map_result;
    int steps;

    public GoalsPresenter(Context context, BaseViewApi view) {
        this.context = context;
        this.view = view;
        model = new GoalsModel();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // 获取当天数据返回结果
                case  Constant.HANDLER_GET_DATA_TODAY_RESULT:
                    String resultStr_data = (String) msg.obj;
                    boolean isResultSuccess_data = ResultUtil.isResultSuccess(resultStr_data);
                    // 成功
                    if (isResultSuccess_data) {
                        Map<String, String> map_data = ResultUtil.parseDataDay(resultStr_data);
                        steps = Integer.parseInt(map_data.get(Constant.KEY_STEPS));
                        // 开始获取目标
                        Map<String, Object> map = new HashMap<>();
                        map.put(Constant.KEY_ID, SpUtils.getInstance().getInt(Constant.KEY_ID, -1));
                        map.put(Constant.KEY_TYPE_POST, Constant.TYPE_GET);
                        model.postGoal(map, handler);
                    }
                    // 失败
                    else {
                        ResultFail fail = ResultUtil.parseFailResult(resultStr_data);
                        view.postFail(fail);
                        view.showToast(context.getString(R.string.public_Failure));
                    }
                    break;
                // 获取目标返回
                case Constant.HANDLER_GET_DAILY_GOAL_RESULT:
                    String resultStrGoal = (String) msg.obj;
                    boolean isResultSuccess_goal = ResultUtil.isResultSuccess(resultStrGoal);
                    // 成功
                    if (isResultSuccess_goal) {
                        String goalSteps = ResultUtil.parseGetDailyGoal(resultStrGoal);
                        int goal = Integer.parseInt(goalSteps);
                        if (steps >= goal) {
                            map_result.put(Constant.KEY_GOAL_BOOL, true);
                        } else {
                            map_result.put(Constant.KEY_GOAL_BOOL, false);
                        }
                        // 开始获取本周目标完成度
                        Map<String, Object> map_week = new HashMap<>();
                        map_week.put(Constant.KEY_ID, SpUtils.getInstance().getInt(Constant.KEY_ID, 0));
//                        Calendar cal_from = CalendarHelper.getDateOfSunday();;
//                        Calendar cal_to = Calendar.getInstance();
//                        map_week.put(Constant.KEY_FROM_DATE, FormatHelper.sdf_yyyy_MM_dd.format(cal_from.getTime()));
//                        map_week.put(Constant.KEY_TO_DATE, FormatHelper.sdf_yyyy_MM_dd.format(cal_to.getTime()));
                        model.postGoalWeekly(map_week, handler);
                    }
                    // 失败
                    else {
                        // 获取失败，可能没有上传过目标，将目标设置为0
                        int goal = 0;
                        if (steps >= goal) {
                            map_result.put(Constant.KEY_GOAL_BOOL, true);
                        } else {
                            map_result.put(Constant.KEY_GOAL_BOOL, false);
                        }
                    }
                    break;
                // 获取本周目标完成度返回
                case Constant.HANDLER_GET_GAOL_BOOL_RESULT:
                    String resultStr = (String) msg.obj;
                    boolean isResultSuccess = ResultUtil.isResultSuccess(resultStr);
                    if (isResultSuccess) {
                        boolean isBool = ResultUtil.isWeelGoalBool(resultStr);
                        map_result.put(Constant.KEY_WEEKLY_GOAL_BOOL, isBool);
                        view.postSuccess(map_result);

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
    public void post(Map<String, Object> maps) {
        if (!InternetUtil.isConnectingToInternet(context)) {
            view.showToast(context.getString(R.string.public_No_network));
            return;
        }

        if (map_result == null) {
            map_result = new HashMap<>();
        }
        map_result.clear();
        steps = 0;

        Global.type_data_time = Constant.TYPE_DAY;
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.KEY_ID, SpUtils.getInstance().getInt(Constant.KEY_ID, 0));
        Calendar cal_from = Calendar.getInstance();
        Calendar cal_to = Calendar.getInstance();
        map.put(Constant.KEY_FROM_DATE, FormatHelper.sdf_yyyy_MM_dd.format(cal_from.getTime()));
        map.put(Constant.KEY_TO_DATE, FormatHelper.sdf_yyyy_MM_dd.format(cal_to.getTime()));

        view.showDialog();
        model.postData(map, handler);
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
