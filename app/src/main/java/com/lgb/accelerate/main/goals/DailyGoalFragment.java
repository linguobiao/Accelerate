package com.lgb.accelerate.main.goals;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;
import com.lgb.accelerate.adapter.EditListener;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.presenter.AddTracksPresenter;
import com.lgb.accelerate.imp.presenter.DailyGoalPresenter;
import com.lgb.accelerate.main.GoalsFragment;
import com.lgb.accelerate.main.TracksFragment;
import com.lgb.accelerate.splash.LoginActivity;
import com.lgb.accelerate.utils.DialogUtils;
import com.lgb.accelerate.utils.FragmentUtils;
import com.lgb.accelerate.utils.KeyBoardUtils;
import com.lgb.accelerate.utils.SpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/17.
 */
public class DailyGoalFragment extends Fragment implements BaseViewApi, View.OnClickListener{

    private BasePresenterApi presenter;
    private ProgressDialog dialog;
    private EditText edit_steps;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goals_daily, container,false);
        initUI(view);
        presenter = new DailyGoalPresenter(getActivity(), this);
        return view;
    }

    private void initUI(View view) {
        view.findViewById(R.id.txt_back).setOnClickListener(this);
        view.findViewById(R.id.txt_done).setOnClickListener(this);
        edit_steps = (EditText) view.findViewById(R.id.edit_steps);
        edit_steps.setOnFocusChangeListener(new EditListener());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_back:
                FragmentUtils.getInstance().returnMainFragment(GoalsFragment.class, FragmentUtils.FRAGMENT_GOALS);
                break;
            case R.id.txt_done:
                Map<String, Object> map = new HashMap<>();
                map.put(Constant.KEY_ID, SpUtils.getInstance().getInt(Constant.KEY_ID, -1));
                map.put(Constant.KEY_STEPS, edit_steps.getText().toString());
                map.put(Constant.KEY_TYPE_POST, Constant.TYPE_ADD);
                presenter.post(map);
                break;
        }
    }

    @Override
    public void showToast(String msg) {
        DialogUtils.hideDialog(dialog);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postSuccess(Object object) {
        DialogUtils.hideDialog(dialog);
        Map<String, Object> map = (Map<String, Object>) object;
        int type = (int) map.get(Constant.KEY_TYPE_POST);
        if (type == Constant.TYPE_GET) {
            String goalSteps = (String) map.get(Constant.KEY_STEPS);
            edit_steps.setText(goalSteps);
        } else {
            FragmentUtils.getInstance().returnMainFragment(GoalsFragment.class, FragmentUtils.FRAGMENT_GOALS);

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
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.KEY_ID, SpUtils.getInstance().getInt(Constant.KEY_ID, -1));
        map.put(Constant.KEY_TYPE_POST, Constant.TYPE_GET);
        presenter.post(map);
    }

    @Override
    public void onStop() {
        super.onStop();
        KeyBoardUtils.hideKeyboard3(getActivity(), edit_steps);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        DialogUtils.dismissDialog(dialog);
    }
}
