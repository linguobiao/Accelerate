package com.lgb.accelerate.main;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.presenter.GoalsPresenter;
import com.lgb.accelerate.imp.presenter.TracksPresenter;
import com.lgb.accelerate.main.goals.DailyGoalFragment;
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
public class GoalsFragment extends Fragment implements BaseViewApi, View.OnClickListener{
    private BasePresenterApi presenter;
    private ProgressDialog dialog;

    private ImageView img_daily, img_weekly;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goals, container,false);
        initUI(view);
        presenter = new GoalsPresenter(getActivity(), this);
        return view;
    }

    private void initUI(View view) {
        view.findViewById(R.id.ly_daily).setOnClickListener(this);
        img_daily = (ImageView) view.findViewById(R.id.img_daily);
        img_weekly = (ImageView) view.findViewById(R.id.img_weekly);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_daily:
                FragmentUtils.getInstance().showFragment(DailyGoalFragment.class, FragmentUtils.FRAGMENT_GOALS_DAILY);
                break;
        }
    }

    private void startPost() {
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.KEY_ID, SpUtils.getInstance().getInt(Constant.KEY_ID, 0));
        presenter.post(map);
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void postSuccess(Object object) {

        DialogUtils.hideDialog(dialog);

        Map<String, Boolean> map = (Map<String, Boolean>) object;
        boolean isDailyOk = map.get(Constant.KEY_GOAL_BOOL);
        boolean isWeeklyOk = map.get(Constant.KEY_WEEKLY_GOAL_BOOL);

        if (isDailyOk) {
            img_daily.setVisibility(View.VISIBLE);
        } else {
            img_daily.setVisibility(View.INVISIBLE);
        }
        if (isWeeklyOk) {
            img_weekly.setVisibility(View.VISIBLE);
        } else {
            img_weekly.setVisibility(View.INVISIBLE);
        }

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
