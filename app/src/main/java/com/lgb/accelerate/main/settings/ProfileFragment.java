package com.lgb.accelerate.main.settings;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.R;
import com.lgb.accelerate.adapter.EditListener;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.bean.User;
import com.lgb.accelerate.imp.presenter.AddTracksPresenter;
import com.lgb.accelerate.imp.presenter.UpdateUserPresenter;
import com.lgb.accelerate.main.SettingsFragment;
import com.lgb.accelerate.main.TracksFragment;
import com.lgb.accelerate.splash.LoginActivity;
import com.lgb.accelerate.utils.DialogUtils;
import com.lgb.accelerate.utils.FormatHelper;
import com.lgb.accelerate.utils.FragmentUtils;
import com.lgb.accelerate.utils.SpUtils;
import com.lgb.accelerate.utils.UnitUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/16.
 */
public class ProfileFragment extends Fragment implements BaseViewApi, View.OnClickListener {

    private BasePresenterApi presenter;
    private ProgressDialog dialog;
    private EditText edit_name, edit_stride, edit_height_ft, edit_height_in, edit_height_cm, edit_weight;
    private TextView txt_name, txt_time, txt_email, txt_stride_unit, txt_height_cm_unit, txt_weight_unit;
    private RelativeLayout ly_height_ft_in;

    private RadioButton radio_m, radio_f;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_profile, container, false);
        initUI(view);
        initValue();
        presenter = new UpdateUserPresenter(getActivity(), this);
        return view;
    }

    private void initUI(View view) {
        view.findViewById(R.id.txt_back).setOnClickListener(this);
        view.findViewById(R.id.txt_done).setOnClickListener(this);
        radio_m = (RadioButton) view.findViewById(R.id.radio_m);
        radio_f = (RadioButton) view.findViewById(R.id.radio_f);
        edit_name = (EditText) view.findViewById(R.id.edit_name);
        edit_stride = (EditText) view.findViewById(R.id.edit_stride);
        edit_height_ft = (EditText) view.findViewById(R.id.edit_height_ft);
        edit_height_in = (EditText) view.findViewById(R.id.edit_height_in);
        edit_height_cm = (EditText) view.findViewById(R.id.edit_height_cm);
        edit_weight = (EditText) view.findViewById(R.id.edit_weight);
        txt_name = (TextView) view.findViewById(R.id.txt_name);
        txt_time = (TextView) view.findViewById(R.id.txt_time);
        txt_email = (TextView) view.findViewById(R.id.txt_email);
        txt_stride_unit = (TextView) view.findViewById(R.id.txt_stride_unit);
        txt_height_cm_unit = (TextView) view.findViewById(R.id.txt_height_cm_unit);
        txt_weight_unit = (TextView) view.findViewById(R.id.txt_weight_unit);
        ly_height_ft_in = (RelativeLayout) view.findViewById(R.id.ly_height_ft_in);
        edit_name.setOnFocusChangeListener(new EditListener());
        edit_stride.setOnFocusChangeListener(new EditListener());
        edit_height_ft.setOnFocusChangeListener(new EditListener());
        edit_height_in.setOnFocusChangeListener(new EditListener());
        edit_height_cm.setOnFocusChangeListener(new EditListener());
        edit_weight.setOnFocusChangeListener(new EditListener());
    }

    private void initValue() {
        txt_name.setText(SpUtils.getInstance().getString(Constant.KEY_NAME, Global.DEF_NAME));
        edit_name.setText(SpUtils.getInstance().getString(Constant.KEY_NAME, Global.DEF_NAME));
        String date = FormatHelper.getDDMonthYYYY(getActivity(), SpUtils.getInstance().getString(Constant.KEY_CREATE_DATE, Global.DEF_CRATE_DATE));
        txt_time.setText(getString(R.string.settings_Joined_on) + " " + date);
        int gender = SpUtils.getInstance().getInt(Constant.KEY_GENDER, Global.DEF_GENDER);
        if (gender == Constant.GENDER_F) {
            radio_f.setChecked(true);
        } else {
            radio_m.setChecked(true);
        }
        txt_email.setText(SpUtils.getInstance().getString(Constant.KEY_EMAIL, Global.DEF_EMAIL));
        double stride = SpUtils.getInstance().getDouble(Constant.KEY_STRIDE, Global.DEF_STRIDE);
        if (!UnitUtils.isMetric(Constant.KEY_UNIT_STRIDE)) {
            stride = UnitUtils.cmToInch(stride);
        }
        edit_stride.setText(FormatHelper.df_0_00().format(stride));
        UnitUtils.initUnit(getActivity(), txt_stride_unit, Constant.KEY_UNIT_STRIDE);
        double height = SpUtils.getInstance().getDouble(Constant.KEY_HEIGHT, Global.DEF_HEIGHT);
        if (UnitUtils.isMetric(Constant.KEY_UNIT_HEIGHT)) {
            edit_height_cm.setText(FormatHelper.df_0_00().format(height));
            ly_height_ft_in.setVisibility(View.INVISIBLE);
            edit_height_cm.setVisibility(View.VISIBLE);
            txt_height_cm_unit.setVisibility(View.VISIBLE);
        } else {
            int[] heightFtIn= UnitUtils.cmToFtIn(height);
            edit_height_ft.setText(String.valueOf(heightFtIn[0]));
            edit_height_in.setText(String.valueOf(heightFtIn[1]));
            ly_height_ft_in.setVisibility(View.VISIBLE);
            edit_height_cm.setVisibility(View.INVISIBLE);
            txt_height_cm_unit.setVisibility(View.INVISIBLE);
        }
        double weight = SpUtils.getInstance().getDouble(Constant.KEY_WEIGHT, Global.DEF_WEIGHT);
        if (!UnitUtils.isMetric(Constant.KEY_UNIT_WEIGHT)) {
            weight = UnitUtils.kgToLbs(weight);
        }
        edit_weight.setText(FormatHelper.df_0_00().format(weight));
        UnitUtils.initUnit(getActivity(), txt_weight_unit, Constant.KEY_UNIT_WEIGHT);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_back:
                FragmentUtils.getInstance().returnMainFragment(SettingsFragment.class, FragmentUtils.FRAGMENT_SETTINGS);
                break;
            case R.id.txt_done:
//                1 英寸 等于 2.54 cm  = 25.4 mm  1 foot (feet英尺) = 12 inch (inches英寸) = 30.48 cm
//                Log.i("testEdit", "value = " + edit_stride.getText().toString());
//                Log.i("testEdit", "boolean = " + edit_stride.getText().toString().equalsIgnoreCase("."));
                Map<String, Object> map = new HashMap<>();
                map.put(Constant.KEY_ID, SpUtils.getInstance().getInt(Constant.KEY_ID, Global.DEF_ID));
                map.put(Constant.KEY_NAME, edit_name.getText().toString());
                if (radio_f.isChecked()) {
                    map.put(Constant.KEY_GENDER, Constant.GENDER_F);
                } else {
                    map.put(Constant.KEY_GENDER, Constant.GENDER_M);
                }
                map.put(Constant.KEY_STRIDE, edit_stride.getText().toString());
                map.put(Constant.KEY_HEIGHT_FT, edit_height_ft.getText().toString());
                map.put(Constant.KEY_HEIGHT_IN, edit_height_in.getText().toString());
                map.put(Constant.KEY_HEIGHT_CM, edit_height_cm.getText().toString());
                map.put(Constant.KEY_WEIGHT, edit_weight.getText().toString());
                Calendar cal = Calendar.getInstance();
                String date = FormatHelper.sdf_yyyy_MM_dd_HH_mm_ss.format(cal.getTime());
                map.put(Constant.KEY_DOB, date);
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
        FragmentUtils.getInstance().returnMainFragment(SettingsFragment.class, FragmentUtils.FRAGMENT_SETTINGS);

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
