package com.lgb.accelerate.imp.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.model.BaseModelApi;
import com.lgb.accelerate.api.other.Data;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.DataSteps;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.model.AddTracksModel;
import com.lgb.accelerate.imp.model.DailyDataModel;
import com.lgb.accelerate.utils.CalendarHelper;
import com.lgb.accelerate.utils.CheckUtils;
import com.lgb.accelerate.utils.FormatHelper;
import com.lgb.accelerate.utils.SpUtils;
import com.lgb.accelerate.utils.http.InternetUtil;
import com.lgb.accelerate.utils.http.ResultUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class DailyDataPresenter implements BasePresenterApi {

    private Context context;
    private BaseViewApi view;
    private BaseModelApi model;

    public DailyDataPresenter(Context context, BaseViewApi view) {
        this.context = context;
        this.view = view;
        model = new DailyDataModel();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLER_GET_DATA_TODAY_RESULT:
                    String resultStr_today = (String) msg.obj;

                    // 成功
                    if (ResultUtil.isResultSuccess(resultStr_today)) {
                        // 今日数据返回
                        Map<String, String> map = ResultUtil.parseDataDay(resultStr_today);
                        view.postSuccess(map);
                    }
                    // 失败
                    else {
                        ResultFail fail = ResultUtil.parseFailResult(resultStr_today);
                        view.postFail(fail);
                        view.showToast(context.getString(R.string.public_Failure));
                    }
                    break;
                case Constant.HANDLER_GET_DATA_WEEK_RESULT:
                    String resultStr_week = (String) msg.obj;

                    // 成功
                    if (ResultUtil.isResultSuccess(resultStr_week)) {
                        // 周或月数据返回
                        List<Data> dataList = ResultUtil.parseData(resultStr_week);
                        view.postSuccess(dataList);
                    }
                    // 失败
                    else {
                        ResultFail fail = ResultUtil.parseFailResult(resultStr_week);
                        view.postFail(fail);
                        view.showToast(context.getString(R.string.public_Failure));
                    }
                    break;
                case Constant.HANDLER_GET_DATA_MONTH_RESULT:
                    String resultStr_month = (String) msg.obj;

                    // 成功
                    if (ResultUtil.isResultSuccess(resultStr_month)) {
                        // 周或月数据返回
                        List<Data> dataList = ResultUtil.parseData(resultStr_month);
                        view.postSuccess(dataList);
                    }
                    // 失败
                    else {
                        ResultFail fail = ResultUtil.parseFailResult(resultStr_month);
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
        Map<String, Object> map_post = new HashMap<>();
        map_post.put(Constant.KEY_ID, SpUtils.getInstance().getInt(Constant.KEY_ID, 0));

        Calendar cal_from = null;
        Calendar cal_to = Calendar.getInstance();
        // 获取今日数据
        if (Global.type_data_time == Constant.TYPE_DAY) {
            cal_from = Calendar.getInstance();
        }
        // 获取这周数据
        else if (Global.type_data_time == Constant.TYPE_WEEK) {
            cal_from = CalendarHelper.getDateOfSunday();
        }
        // 获取一个自然月数据
        else if (Global.type_data_time == Constant.TYPE_MONTH) {
            cal_from = CalendarHelper.getMonthBegin((Calendar) map.get(Constant.KEY_DATE_POST));
            cal_to = CalendarHelper.getMonthEnd((Calendar) map.get(Constant.KEY_DATE_POST));
        }

        map_post.put(Constant.KEY_FROM_DATE, FormatHelper.sdf_yyyy_MM_dd.format(cal_from.getTime()));
        map_post.put(Constant.KEY_TO_DATE, FormatHelper.sdf_yyyy_MM_dd.format(cal_to.getTime()));
        view.showDialog();
        model.post(map_post, handler);
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
