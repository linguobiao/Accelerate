package com.lgb.accelerate.main;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.presenter.DailyDataPresenter;
import com.lgb.accelerate.main.dashbord.DetailFragment;
import com.lgb.accelerate.splash.LoginActivity;
import com.lgb.accelerate.utils.DialogUtils;
import com.lgb.accelerate.utils.FragmentUtils;
import com.lgb.accelerate.utils.SpUtils;
import com.lgb.accelerate.utils.UnitUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/15.
 */
public class DashboardFragment extends Fragment implements BaseViewApi, View.OnClickListener{
    private BasePresenterApi presenter;
    private ProgressDialog dialog;
    private TextView txt_steps, txt_distance, txt_calories;
    private TextView txt_unit_distance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        initUI(view);
        presenter = new DailyDataPresenter(getActivity(), this);
        return view;
    }

    private void initUI(View view) {
        view.findViewById(R.id.ly_steps).setOnClickListener(this);
        view.findViewById(R.id.ly_distance).setOnClickListener(this);
        view.findViewById(R.id.ly_burn).setOnClickListener(this);

        txt_steps = (TextView) view.findViewById(R.id.txt_steps);
        txt_distance = (TextView) view.findViewById(R.id.txt_distance);
        txt_calories = (TextView) view.findViewById(R.id.txt_calories);
        txt_unit_distance = (TextView) view.findViewById(R.id.txt_unit_distance);
    }

    private void startPost() {
        Global.type_data_time = Constant.TYPE_DAY;
        presenter.post(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_steps:
                Global.type_data = Constant.TYPE_STEPS;
                Global.value_today = txt_steps.getText().toString();
                FragmentUtils.getInstance().showFragment(DetailFragment.class, FragmentUtils.FRAGMENT_DASHBOARD_DETAIL);
                break;
            case R.id.ly_distance:
                Global.type_data = Constant.TYPE_DISTANCE;
                Global.value_today = txt_distance.getText().toString();
                FragmentUtils.getInstance().showFragment(DetailFragment.class, FragmentUtils.FRAGMENT_DASHBOARD_DETAIL);
                break;
            case R.id.ly_burn:
                Global.type_data = Constant.TYPE_CALORIES;
                Global.value_today = txt_calories.getText().toString();
                FragmentUtils.getInstance().showFragment(DetailFragment.class, FragmentUtils.FRAGMENT_DASHBOARD_DETAIL);
                break;
        }
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void postSuccess(Object object) {

        DialogUtils.hideDialog(dialog);
        Map<String, String> map = (Map<String, String>)object;
        txt_steps.setText(map.get(Constant.KEY_STEPS));
        txt_distance.setText(map.get(Constant.KEY_DISTANCES));
        txt_calories.setText(map.get(Constant.KEY_CALORIES));

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
            UnitUtils.initUnit(getActivity(), txt_unit_distance, Constant.KEY_UNIT_DISTANCE);
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
            UnitUtils.initUnit(getActivity(), txt_unit_distance, Constant.KEY_UNIT_DISTANCE);
            startPost();
        }
    }
}
