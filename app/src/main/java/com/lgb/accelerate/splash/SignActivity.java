package com.lgb.accelerate.splash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;
import com.lgb.accelerate.adapter.EditListener;
import com.lgb.accelerate.adapter.ViewPagerAdapter;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.presenter.SignPresenter;
import com.lgb.accelerate.utils.DialogUtils;
import com.lgb.accelerate.utils.FormatHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/16.
 */
public class SignActivity extends Activity implements BaseViewApi, View.OnClickListener{

    private ViewPager viewPager_sign;
    private ViewPagerAdapter adapter;
    private TextView txt_next;
    private ProgressDialog dialog;
    private BasePresenterApi presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        initUI();
        presenter = new SignPresenter(this, this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_back:
                if (viewPager_sign.getCurrentItem() == 0) {
                    SignActivity.this.finish();

                } else {
                    viewPager_sign.setCurrentItem(0);
                }
                break;
            case R.id.txt_next:
                viewPager_sign.setCurrentItem(1);
                break;
            case R.id.txt_sign:

                Map<String, Object> map = new HashMap<>();
                map.put(Constant.KEY_NAME, edit_name.getText().toString());
                map.put(Constant.KEY_EMAIL, edit_email.getText().toString());
                map.put(Constant.KEY_PASSWORD, edit_password.getText().toString());
                map.put(Constant.KEY_PASSWORD_CONFIRM, edit_password_confirm.getText().toString());
                if (radio_f.isChecked()) {
                    map.put(Constant.KEY_GENDER, Constant.GENDER_F);
                } else {
                    map.put(Constant.KEY_GENDER, Constant.GENDER_M);
                }
                map.put(Constant.KEY_STRIDE, edit_stride.getText().toString());
                map.put(Constant.KEY_HEIGHT_FT, edit_height_ft.getText().toString());
                map.put(Constant.KEY_HEIGHT_IN, edit_height_in.getText().toString());
                map.put(Constant.KEY_HEIGHT_CM, null);
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postSuccess(Object object) {
        sendBroadcast(new Intent(Constant.INTENT_FINISH_WELCOME_ACTIVITY));
        startActivity(new Intent(this, LoginActivity.class));
        this.finish();
    }

    @Override
    public void postFail(ResultFail fail) {

    }

    @Override
    public void showDialog() {
        if (dialog == null) {
            dialog = DialogUtils.showProgressDialog(this, getString(R.string.public_loading));
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
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        DialogUtils.dismissDialog(dialog);
    }


    private class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // Log.i(tag, "chandged arg0 = " + arg0);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                txt_next.setVisibility(View.VISIBLE);
                img_point_left.setImageDrawable(getResources().getDrawable(R.mipmap.image_point_on));
                img_point_right.setImageDrawable(getResources().getDrawable(R.mipmap.image_point_off));
            } else {
                txt_next.setVisibility(View.INVISIBLE);
                img_point_left.setImageDrawable(getResources().getDrawable(R.mipmap.image_point_off));
                img_point_right.setImageDrawable(getResources().getDrawable(R.mipmap.image_point_on));
            }
        }
    }

    private void initTip() {
        SpannableString ss = new SpannableString(getString(R.string.sign_tip));

        ss.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.click));       //设置文件颜色
                ds.setUnderlineText(false);      //设置下划线
            }
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(SignActivity.this, PocilyActivity.class));
//                Toast.makeText(SignActivity.this, "dddddddss", Toast.LENGTH_SHORT).show();
            }
        }, 52, 70, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.click));       //设置文件颜色
                ds.setUnderlineText(false);      //设置下划线
            }
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(SignActivity.this, PocilyActivity.class));
//                Toast.makeText(SignActivity.this, "dddddddss", Toast.LENGTH_SHORT).show();
            }
        }, 75, 89, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txt_tip.setText(ss);
        txt_tip.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initUI() {
        findViewById(R.id.txt_back).setOnClickListener(this);
        img_point_left = (ImageView) findViewById(R.id.img_point_left);
        img_point_right = (ImageView) findViewById(R.id.img_point_right);
        txt_next = (TextView) findViewById(R.id.txt_next);
        txt_next.setOnClickListener(this);
        viewPager_sign = (ViewPager)findViewById(R.id.viewPager_sign);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.view_sign_1, new LinearLayout(this), false);
        View view2 = inflater.inflate(R.layout.view_sign_2, new LinearLayout(this), false);
        radio_m = (RadioButton) view1.findViewById(R.id.radio_m);
        radio_m.setChecked(true);

        radio_f = (RadioButton) view1.findViewById(R.id.radio_f);
        edit_name = (EditText) view1.findViewById(R.id.edit_name);
        edit_email = (EditText) view1.findViewById(R.id.edit_email);
        edit_password = (EditText) view1.findViewById(R.id.edit_password);
        edit_password_confirm = (EditText) view1.findViewById(R.id.edit_password_confirm);
        edit_stride = (EditText) view2.findViewById(R.id.edit_stride);
        edit_height_ft = (EditText) view2.findViewById(R.id.edit_height_ft);
        edit_height_in = (EditText) view2.findViewById(R.id.edit_height_in);
        edit_weight = (EditText) view2.findViewById(R.id.edit_weight);
        txt_tip = (TextView) view2.findViewById(R.id.txt_tip);
        initTip();

        edit_stride.setOnFocusChangeListener(new EditListener());
        edit_height_ft.setOnFocusChangeListener(new EditListener());
        edit_height_in.setOnFocusChangeListener(new EditListener());
        edit_weight.setOnFocusChangeListener(new EditListener());

        view2.findViewById(R.id.txt_sign).setOnClickListener(this);
        List<View> viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);

        adapter = new ViewPagerAdapter();

        viewPager_sign.setAdapter(adapter);
        viewPager_sign.setOnPageChangeListener(new GuidePageChangeListener());

        adapter.setList(viewList);
        adapter.notifyDataSetChanged();
    }

    private EditText edit_name, edit_email, edit_password, edit_password_confirm, edit_stride, edit_height_ft, edit_height_in, edit_weight;

    private TextView txt_tip;
    private RadioButton radio_m, radio_f;
    private ImageView img_point_left, img_point_right;
}
