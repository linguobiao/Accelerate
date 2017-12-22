package com.lgb.accelerate.main.tracks;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.R;
import com.lgb.accelerate.adapter.EditListener;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.presenter.AddTracksPresenter;
import com.lgb.accelerate.main.GoalsFragment;
import com.lgb.accelerate.main.TracksFragment;
import com.lgb.accelerate.splash.LoginActivity;
import com.lgb.accelerate.utils.CalculateUtils;
import com.lgb.accelerate.utils.DialogUtils;
import com.lgb.accelerate.utils.FragmentUtils;
import com.lgb.accelerate.utils.KeyBoardUtils;
import com.lgb.accelerate.utils.SpUtils;
import com.lgb.accelerate.utils.UnitUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/17.
 */
public class AddTrackFragment extends Fragment implements BaseViewApi, View.OnClickListener {

    private BasePresenterApi presenter;
    private ProgressDialog dialog;
    private EditText edit_steps;
    private TextView txt_distance, txt_cal;
    private TextView txt_unit_distance;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracks_add, container, false);
        initUI(view);
        UnitUtils.initUnit(getActivity(), txt_unit_distance, Constant.KEY_UNIT_DISTANCE);
        presenter = new AddTracksPresenter(getActivity(), this);
        return view;
    }

    private void initUI(View view) {
        view.findViewById(R.id.txt_back).setOnClickListener(this);
        view.findViewById(R.id.txt_done).setOnClickListener(this);
        txt_distance = (TextView) view.findViewById(R.id.txt_distance);
        txt_cal = (TextView) view.findViewById(R.id.txt_calories);
        edit_steps = (EditText) view.findViewById(R.id.edit_steps);
        edit_steps.setOnFocusChangeListener(new EditListener());
        edit_steps.addTextChangedListener(watcher);
        txt_unit_distance = (TextView) view.findViewById(R.id.txt_unit_distance);

    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i("profile", "s = " + s + ",   start = " + start + ",   before = " + before + ",   count = " + count);
            if (s != null && !s.toString().equals("") && start >= 0) {

                String distance = CalculateUtils.calculateDistance(Integer.parseInt(s.toString()), SpUtils.getInstance().getDouble(Constant.KEY_STRIDE, Global.DEF_STRIDE));
                String cal = CalculateUtils.calculateCalories(Integer.parseInt(s.toString()), SpUtils.getInstance().getDouble(Constant.KEY_WEIGHT, Global.DEF_WEIGHT));


                txt_distance.setText(distance);
                txt_cal.setText(cal);
                return;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            edit_steps.setSelection(edit_steps.length());
        }

        @Override
        public void afterTextChanged(Editable s) {
//            edit_steps.setSelection(edit_steps.length());
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_back:
                FragmentUtils.getInstance().returnMainFragment(TracksFragment.class, FragmentUtils.FRAGMENT_TRACKS);
                break;
            case R.id.txt_done:
                Map<String, Object> map = new HashMap<>();
                map.put(Constant.KEY_ID, SpUtils.getInstance().getInt(Constant.KEY_ID, -1));
                map.put(Constant.KEY_STEPS, edit_steps.getText().toString());
                map.put(Constant.KEY_WEIGHT, SpUtils.getInstance().getDouble(Constant.KEY_WEIGHT, 0));
                map.put(Constant.KEY_STRIDE, SpUtils.getInstance().getDouble(Constant.KEY_STRIDE, 24));
                presenter.post(map);
                break;
        }
    }

    @Override
    public void showToast(String msg) {
        DialogUtils.hideDialog(dialog);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        Log.i("test", "toast = " + msg);
    }

    @Override
    public void postSuccess(Object object) {
        DialogUtils.hideDialog(dialog);
        FragmentUtils.getInstance().returnMainFragment(TracksFragment.class, FragmentUtils.FRAGMENT_TRACKS);

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
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden == false) {
            UnitUtils.initUnit(getActivity(), txt_unit_distance, Constant.KEY_UNIT_DISTANCE);
        }
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
