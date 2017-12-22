package com.lgb.accelerate.main;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.presenter.TracksPresenter;
import com.lgb.accelerate.main.tracks.AddTrackFragment;
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
public class TracksFragment extends Fragment implements BaseViewApi, View.OnClickListener{
    private BasePresenterApi presenter;
    private ProgressDialog dialog;
    private TextView txt_steps, txt_distance, txt_calories;
    private TextView txt_unit_distance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracks, container, false);
        initUI(view);
        UnitUtils.initUnit(getActivity(), txt_unit_distance, Constant.KEY_UNIT_DISTANCE);
        presenter = new TracksPresenter(getActivity(), this);
        return view;
    }

    private void initUI(View view) {
        view.findViewById(R.id.txt_add).setOnClickListener(this);
        txt_steps = (TextView) view.findViewById(R.id.txt_steps);
        txt_distance = (TextView) view.findViewById(R.id.txt_distance);
        txt_calories = (TextView) view.findViewById(R.id.txt_calories);
        txt_unit_distance = (TextView) view.findViewById(R.id.txt_unit_distance);

    }

    private void startPost() {
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.KEY_ID, SpUtils.getInstance().getInt(Constant.KEY_ID, 0));
        presenter.post(map);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_add:
                FragmentUtils.getInstance().showFragment(AddTrackFragment.class, FragmentUtils.FRAGMENT_TRACKS_ADD);
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
