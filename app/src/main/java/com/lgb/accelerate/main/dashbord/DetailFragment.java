package com.lgb.accelerate.main.dashbord;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.R;
import com.lgb.accelerate.adapter.DataListViewAdapter;
import com.lgb.accelerate.api.other.Data;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.DataSteps;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.presenter.DailyDataPresenter;
import com.lgb.accelerate.main.DashboardFragment;
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
import java.util.List;

/**
 * Created by linguobiao on 16/8/17.
 */
public class DetailFragment extends Fragment implements BaseViewApi, View.OnClickListener{
    private BasePresenterApi presenter;
    private ProgressDialog dialog;
    private TextView txt_title, txt_label_chart, txt_value, txt_label, txt_average;
    private RectView view_rect;
    private ChartBarView view_chart;
    private ListView lv_data;

    private List<Data> historyList_weekly = new ArrayList<>();
    private List<Data> dataList_week = new ArrayList<>();

    private DataListViewAdapter listViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_detail, container,false);
        initUI(view);

        initRect(historyList_weekly);
        initValue();

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
        view.findViewById(R.id.txt_search).setOnClickListener(this);
        view_rect = (RectView)view.findViewById(R.id.view_rect);
        view_chart = (ChartBarView)view.findViewById(R.id.view_chart);
        lv_data = (ListView) view.findViewById(R.id.lv_data);
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

        view_chart.setList(historyList_weekly);
        view_chart.initView();
        listViewAdapter = new DataListViewAdapter(getActivity(), dataList_week);
        lv_data.setAdapter(listViewAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_back:
                FragmentUtils.getInstance().returnMainFragment(DashboardFragment.class, FragmentUtils.FRAGMENT_DASHBOARD);
                break;
            case R.id.txt_search:
                FragmentUtils.getInstance().showFragment(DetailMonthFragment.class, FragmentUtils.FRAGMENT_DASHBOARD_MONTH);
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
        Global.type_data_time = Constant.TYPE_WEEK;
        presenter.post(null);
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
        view_chart.initView();

        dataList_week.clear();
        dataList_week.addAll(CalculateUtils.calculateWeekList(historyList_weekly));
        listViewAdapter.notifymDataSetChanged(dataList_week);

        txt_average.setText(getString(R.string.dash_Weekly_Average) + ": " + CalculateUtils.getWeekAverage(historyList_weekly));
        txt_value.setText(Global.value_today);

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
}
