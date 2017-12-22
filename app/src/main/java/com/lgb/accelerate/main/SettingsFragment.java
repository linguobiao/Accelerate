package com.lgb.accelerate.main;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.presenter.SettingsPresenter;
import com.lgb.accelerate.main.settings.FaqActivity;
import com.lgb.accelerate.main.settings.ProfileFragment;
import com.lgb.accelerate.main.settings.UnitFragment;
import com.lgb.accelerate.splash.LoginActivity;
import com.lgb.accelerate.splash.WelcomeActivity;
import com.lgb.accelerate.utils.DialogUtils;
import com.lgb.accelerate.utils.FormatHelper;
import com.lgb.accelerate.utils.FragmentUtils;
import com.lgb.accelerate.utils.KeyBoardUtils;
import com.lgb.accelerate.utils.SpUtils;
import com.lgb.accelerate.utils.UnitUtils;

import java.text.ParseException;

/**
 * Created by linguobiao on 16/8/15.
 */
public class SettingsFragment extends Fragment implements BaseViewApi,  View.OnClickListener{
    private BasePresenterApi presenter;
    private ProgressDialog dialog;
    private Dialog dialog_reset;

    private TextView txt_name, txt_time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        initUI(view);
        initValue();
        presenter = new SettingsPresenter(getActivity(), this);
        return view;
    }

    private void initUI(View view) {
        txt_name = (TextView) view.findViewById(R.id.txt_name);
        txt_time = (TextView) view.findViewById(R.id.txt_time);
        view.findViewById(R.id.ly_profile).setOnClickListener(this);
        view.findViewById(R.id.ly_unit).setOnClickListener(this);
        view.findViewById(R.id.ly_faq).setOnClickListener(this);
        view.findViewById(R.id.txt_reset).setOnClickListener(this);
        view.findViewById(R.id.txt_logout).setOnClickListener(this);
    }

    private void initValue() {
        String name = SpUtils.getInstance().getString(Constant.KEY_NAME, null);
        String time = SpUtils.getInstance().getString(Constant.KEY_CREATE_DATE, null);
        txt_name.setText(name);
        String date = FormatHelper.getDDMonthYYYY(getActivity(), time);
        txt_time.setText(getString(R.string.settings_Joined_on) + " " + date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_profile:
                FragmentUtils.getInstance().showFragment(ProfileFragment.class, FragmentUtils.FRAGMENT_SETTINGS_PROFILE);
                break;
            case R.id.ly_unit:
                FragmentUtils.getInstance().showFragment(UnitFragment.class, FragmentUtils.FRAGMENT_SETTINGS_UNIT);
                break;
            case R.id.ly_faq:
                startActivity(new Intent(getActivity(), FaqActivity.class));
                break;
            case R.id.txt_reset:
                showResetDialog();

                break;
            case R.id.txt_logout:
                SpUtils.getInstance().clear();  // 清除所有数据
                getActivity().startActivity(new Intent(getActivity(), WelcomeActivity.class));
                getActivity().finish();
                break;
        }
    }

    private void showResetDialog() {
        DialogUtils.showAlertDialog_2(getActivity(), getString(R.string.settings_R_Y_A), getString(R.string.settings_tip_reset), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.post(null);
            }
        }, null);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initValue();
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
        SpUtils.getInstance().clear();  // 清除所有数据
        getActivity().startActivity(new Intent(getActivity(), WelcomeActivity.class));
        getActivity().finish();

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
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        DialogUtils.dismissDialog(dialog);
    }
}
