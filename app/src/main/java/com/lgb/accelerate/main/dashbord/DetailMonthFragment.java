package com.lgb.accelerate.main.dashbord;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.other.Data;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.presenter.DailyDataPresenter;
import com.lgb.accelerate.splash.LoginActivity;
import com.lgb.accelerate.utils.CalculateUtils;
import com.lgb.accelerate.utils.CalendarHelper;
import com.lgb.accelerate.utils.DialogUtils;
import com.lgb.accelerate.utils.FormatHelper;
import com.lgb.accelerate.utils.FragmentUtils;
import com.lgb.accelerate.utils.SpUtils;
import com.lgb.accelerate.utils.UnitUtils;
import com.lgb.accelerate.view.ChartBarView;
import com.lgb.accelerate.view.RectView;

import org.xclcharts.common.DensityUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/17.
 */
public class DetailMonthFragment extends Fragment implements BaseViewApi, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private BasePresenterApi presenter;
    private ProgressDialog dialog;
    private TextView txt_title, txt_label_chart, txt_value, txt_label, txt_average, txt_month;
    private ImageView img_left, img_right;
    private RadioButton radio_week, radio_month;
    private RectView view_rect;
    private ChartBarView view_chart;

    private List<Data> historyList_weekly = new ArrayList<>();
    private Calendar cal_current;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_month, container,false);
        initUI(view);
        cal_current = Calendar.getInstance();

        initRect(historyList_weekly);
        initValue();
        initMonth(false);

        presenter = new DailyDataPresenter(getActivity(), this);
        return view;
    }

    private void initUI(View view) {
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_label_chart = (TextView) view.findViewById(R.id.txt_label_chart);
        txt_value = (TextView) view.findViewById(R.id.txt_value);
        txt_label = (TextView) view.findViewById(R.id.txt_label);
        txt_average = (TextView) view.findViewById(R.id.txt_average);
        view.findViewById(R.id.txt_back).setOnClickListener(this);
        view_rect = (RectView)view.findViewById(R.id.view_rect);
        view_chart = (ChartBarView)view.findViewById(R.id.view_chart);
        radio_week = (RadioButton) view.findViewById(R.id.radio_week);
        radio_month = (RadioButton) view.findViewById(R.id.radio_month);
        radio_week.setOnCheckedChangeListener(this);
        radio_month.setOnCheckedChangeListener(this);

        txt_month = (TextView) view.findViewById(R.id.txt_month);
        img_left = (ImageView) view.findViewById(R.id.img_left);
        img_right = (ImageView) view.findViewById(R.id.img_right);
        img_left.setOnClickListener(this);
        img_right.setOnClickListener(this);
    }

    private void initValue() {

        if (Global.type_data == Constant.TYPE_STEPS) {
            txt_title.setText(R.string.dash_STEPS);
            txt_label_chart.setText(R.string.dash_Number_Of_Steps);
            txt_label.setText(R.string.public_steps);
            txt_title.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.image_steps, 0, 0, 0);
        } else if (Global.type_data == Constant.TYPE_DISTANCE) {
            txt_title.setText(R.string.dash_DISTANCE);
            txt_label_chart.setText(R.string.dash_Total_distance);
            UnitUtils.initUnit(getActivity(), txt_label, Constant.KEY_UNIT_DISTANCE);
            txt_title.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.image_distance, 0, 0, 0);
        } else if (Global.type_data == Constant.TYPE_CALORIES) {
            txt_title.setText(R.string.dash_CALORIES);
            txt_label_chart.setText(R.string.dash_Total_calories);
            txt_label.setText(R.string.public_calories_burned);
            txt_title.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.image_burn, 0, 0, 0);
        }
        txt_month.setText(FormatHelper.getMonthStr(getActivity(), cal_current));

        view_chart.setList(historyList_weekly);
        view_chart.initView();

    }

    private void initMonth(boolean isMonth) {
        if (isMonth) {
            txt_month.setVisibility(View.VISIBLE);
            img_left.setVisibility(View.VISIBLE);
            img_right.setVisibility(View.VISIBLE);
        } else {
            txt_month.setVisibility(View.INVISIBLE);
            img_left.setVisibility(View.INVISIBLE);
            img_right.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_back:
                FragmentUtils.getInstance().removeFragment(FragmentUtils.FRAGMENT_DASHBOARD_MONTH);
                FragmentUtils.getInstance().showFragment(DetailFragment.class, FragmentUtils.FRAGMENT_DASHBOARD_DETAIL);
                break;
            case R.id.img_left:
                cal_current.set(Calendar.MONTH, cal_current.get(Calendar.MONTH) - 1);
                startPost();
                break;
            case R.id.img_right:
                cal_current.set(Calendar.MONTH, cal_current.get(Calendar.MONTH) + 1);
                startPost();
                break;
        }
    }

    private void initRect(List<Data> heartProcessList) {

        int[] ltrb = new int[4];
        ltrb[0] = DensityUtil.dip2px(getActivity(), 25); //left
        ltrb[1] = DensityUtil.dip2px(getActivity(), 20); //top
        ltrb[2] = DensityUtil.dip2px(getActivity(), 10); //right
        ltrb[3] = DensityUtil.dip2px(getActivity(), 50); //bottom
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view_rect.getLayoutParams();

        params.setMargins(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
        view_rect.setLayoutParams(params);

        view_rect.setDrawData(0, 20, 10, 0, ltrb);

    }

    private void startPost() {
        if (radio_month.isChecked()) {
            Map<String, Object> map = new HashMap<>();
            map.put(Constant.KEY_DATE_POST, cal_current);
            Global.type_data_time = Constant.TYPE_MONTH;
            presenter.post(map);
        } else {
            Global.type_data_time = Constant.TYPE_WEEK;
            presenter.post(null);
        }
    }


    @Override
    public void showToast(String msg) {

    }

    @Override
    public void postSuccess(Object object) {

        DialogUtils.hideDialog(dialog);

        historyList_weekly = (List<Data>) object;
        initRect(historyList_weekly);
        view_chart.setList(historyList_weekly);
        view_chart.setCurrentDate(cal_current);
        view_chart.initView();

        txt_average.setText(getString(R.string.dash_Weekly_Average) + ": " + CalculateUtils.getWeekAverage(historyList_weekly));
        txt_value.setText(Global.value_today);

        txt_month.setText(FormatHelper.getMonthStr(getActivity(), cal_current));
        initMonth(Global.type_data_time == Constant.TYPE_MONTH);

    }

    @Override
    public void postFail(ResultFail fail) {
        if (fail != null) {
            if (fail.getCode() == Constant.HTTP_CODE_2){
                SpUtils.getInstance().clear();  // 清除所有数据
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        }
    }

    @Override
    public void showDialog() {
        if (dialog == null) {
            dialog = DialogUtils.showProgressDialog(getActivity(), getString(R.string.public_loading));
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    presenter.stopLoad();
                }
            });
        }
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isHidden()) {
            startPost();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        DialogUtils.dismissDialog(dialog);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            startPost();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.radio_week:
                if (isChecked) {
                    initMonth(false);
                    Global.type_data_time = Constant.TYPE_WEEK;
                    presenter.post(null);
                }
                break;
            case R.id.radio_month:
                if (isChecked) {
                    cal_current = CalendarHelper.getToday();
                    Map<String, Object> map = new HashMap<>();
                    map.put(Constant.KEY_DATE_POST, cal_current);
                    Global.type_data_time = Constant.TYPE_MONTH;
                    presenter.post(map);
                }
                break;
        }
    }
}
