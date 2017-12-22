package com.lgb.accelerate.main.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;
import com.lgb.accelerate.main.SettingsFragment;
import com.lgb.accelerate.utils.FragmentUtils;
import com.lgb.accelerate.utils.SpUtils;

/**
 * Created by linguobiao on 16/8/16.
 */
public class UnitFragment extends Fragment implements View.OnClickListener {

    private ImageView img_miles, img_km;
    private ImageView img_ft_in, img_cm;
    private ImageView img_lbs, img_kg;
    private ImageView img_inch, img_m;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_unit, container, false);

        initUI(view);
        initValue();
        return view;
    }

    private void initUI(View view) {
        view.findViewById(R.id.txt_back).setOnClickListener(this);
        view.findViewById(R.id.txt_done).setOnClickListener(this);
        view.findViewById(R.id.ly_miles).setOnClickListener(this);
        view.findViewById(R.id.ly_km).setOnClickListener(this);
        view.findViewById(R.id.ly_ft_in).setOnClickListener(this);
        view.findViewById(R.id.ly_cm).setOnClickListener(this);
        view.findViewById(R.id.ly_lbs).setOnClickListener(this);
        view.findViewById(R.id.ly_kg).setOnClickListener(this);
        view.findViewById(R.id.ly_inch).setOnClickListener(this);
        view.findViewById(R.id.ly_m).setOnClickListener(this);

        img_miles = (ImageView) view.findViewById(R.id.img_miles);
        img_km = (ImageView) view.findViewById(R.id.img_km);
        img_ft_in = (ImageView) view.findViewById(R.id.img_ft_in);
        img_cm = (ImageView) view.findViewById(R.id.img_cm);
        img_lbs = (ImageView) view.findViewById(R.id.img_lbs);
        img_kg = (ImageView) view.findViewById(R.id.img_kg);
        img_inch = (ImageView) view.findViewById(R.id.img_inch);
        img_m = (ImageView) view.findViewById(R.id.img_m);

    }

    private void initValue() {
        int distance = SpUtils.getInstance().getInt(Constant.KEY_UNIT_DISTANCE, Constant.UNIT_IMPERIAL);
        int height = SpUtils.getInstance().getInt(Constant.KEY_UNIT_HEIGHT, Constant.UNIT_IMPERIAL);
        int weight = SpUtils.getInstance().getInt(Constant.KEY_UNIT_WEIGHT, Constant.UNIT_IMPERIAL);
        int stride = SpUtils.getInstance().getInt(Constant.KEY_UNIT_STRIDE, Constant.UNIT_IMPERIAL);
        if (distance == Constant.UNIT_METRIC) {
            img_miles.setVisibility(View.INVISIBLE);
            img_km.setVisibility(View.VISIBLE);
        } else {
            img_miles.setVisibility(View.VISIBLE);
            img_km.setVisibility(View.INVISIBLE);

        }
        if (height == Constant.UNIT_METRIC) {
            img_ft_in.setVisibility(View.INVISIBLE);
            img_cm.setVisibility(View.VISIBLE);
        } else {
            img_ft_in.setVisibility(View.VISIBLE);
            img_cm.setVisibility(View.INVISIBLE);

        }
        if (weight == Constant.UNIT_METRIC) {
            img_lbs.setVisibility(View.INVISIBLE);
            img_kg.setVisibility(View.VISIBLE);
        } else {
            img_lbs.setVisibility(View.VISIBLE);
            img_kg.setVisibility(View.INVISIBLE);

        }
        if (stride == Constant.UNIT_METRIC) {
            img_inch.setVisibility(View.INVISIBLE);
            img_m.setVisibility(View.VISIBLE);
        } else {
            img_inch.setVisibility(View.VISIBLE);
            img_m.setVisibility(View.INVISIBLE);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_back:
                FragmentUtils.getInstance().returnMainFragment(SettingsFragment.class, FragmentUtils.FRAGMENT_SETTINGS);
                break;
            case R.id.txt_done:
                SpUtils.getInstance().putInt(Constant.KEY_UNIT_DISTANCE, img_km.getVisibility() == View.VISIBLE ? Constant.UNIT_METRIC : Constant.UNIT_IMPERIAL);
                SpUtils.getInstance().putInt(Constant.KEY_UNIT_HEIGHT, img_cm.getVisibility() == View.VISIBLE ? Constant.UNIT_METRIC : Constant.UNIT_IMPERIAL);
                SpUtils.getInstance().putInt(Constant.KEY_UNIT_WEIGHT, img_kg.getVisibility() == View.VISIBLE ? Constant.UNIT_METRIC : Constant.UNIT_IMPERIAL);
                SpUtils.getInstance().putInt(Constant.KEY_UNIT_STRIDE, img_m.getVisibility() == View.VISIBLE ? Constant.UNIT_METRIC : Constant.UNIT_IMPERIAL);
                Toast.makeText(getActivity(), getString(R.string.settings_Updated), Toast.LENGTH_SHORT).show();
                FragmentUtils.getInstance().returnMainFragment(SettingsFragment.class, FragmentUtils.FRAGMENT_SETTINGS);
                break;
            case R.id.ly_miles:
                img_miles.setVisibility(View.VISIBLE);
                img_km.setVisibility(View.INVISIBLE);
                break;
            case R.id.ly_km:
                img_miles.setVisibility(View.INVISIBLE);
                img_km.setVisibility(View.VISIBLE);
                break;
            case R.id.ly_ft_in:
                img_ft_in.setVisibility(View.VISIBLE);
                img_cm.setVisibility(View.INVISIBLE);
                break;
            case R.id.ly_cm:
                img_ft_in.setVisibility(View.INVISIBLE);
                img_cm.setVisibility(View.VISIBLE);
                break;
            case R.id.ly_lbs:
                img_lbs.setVisibility(View.VISIBLE);
                img_kg.setVisibility(View.INVISIBLE);
                break;
            case R.id.ly_kg:
                img_lbs.setVisibility(View.INVISIBLE);
                img_kg.setVisibility(View.VISIBLE);
                break;
            case R.id.ly_inch:
                img_inch.setVisibility(View.VISIBLE);
                img_m.setVisibility(View.INVISIBLE);
                break;
            case R.id.ly_m:
                img_inch.setVisibility(View.INVISIBLE);
                img_m.setVisibility(View.VISIBLE);
                break;
        }

    }
}
